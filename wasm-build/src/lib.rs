extern crate lumis;
extern crate strum;

use std::ptr;

use lumis::{languages::Language, themes, HtmlInlineBuilder, HtmlLinkedBuilder, TerminalBuilder};
use strum::IntoEnumIterator;

// Import malloc and free from WASI-SDK C library
extern "C" {
    fn malloc(size: usize) -> *mut u8;
    fn free(ptr: *mut u8);
}

#[no_mangle]
pub extern "C" fn wasm_malloc(size: usize) -> *mut u8 {
    unsafe { malloc(size) }
}

#[no_mangle]
pub extern "C" fn wasm_free(ptr: *mut u8) {
    unsafe { free(ptr) }
}

#[no_mangle]
pub extern "C" fn highlight(
    code_ptr: i32,
    code_len: i32,
    theme_ptr: i32,
    theme_len: i32,
    lang_ptr: i32,
    lang_len: i32,
    formatter_type: i32,
) -> i64 {
    let code = consume_string(code_ptr, code_len);
    let theme_name = consume_string(theme_ptr, theme_len);
    let lang = consume_string(lang_ptr, lang_len);

    // println!("lang: {:?}", lang);
    // println!("theme: {:?}", theme_name);
    // println!("code: {:?}", code);
    // println!("formatter_type: {:?}", formatter_type);

    let theme = match themes::get(&theme_name) {
        Ok(theme) => theme,
        Err(e) => {
            let error_msg = format!("error: theme `{}` not found: {}", theme_name, e);
            eprintln!("{}", error_msg);
            return leak_string(error_msg);
        }
    };

    let language = Language::guess(Some(&lang), "");

    // println!("selected language: {:?}", language);

    let formatter = build_formatter(language, theme, formatter_type);

    let output = lumis::highlight(&code, formatter);

    leak_string(output.to_string())
}

fn build_formatter(
    language: Language,
    theme: themes::Theme,
    formatter_type: i32,
) -> Box<dyn lumis::formatter::Formatter> {
    match formatter_type {
        1 => {
            // Terminal formatter
            Box::new(
                TerminalBuilder::new()
                    .lang(language)
                    .theme(Some(theme))
                    .build()
                    .expect("Failed to build terminal formatter"),
            )
        }
        2 => {
            // HTML inline formatter
            Box::new(
                HtmlInlineBuilder::new()
                    .lang(language)
                    .theme(Some(theme))
                    .build()
                    .expect("Failed to build HTML inline formatter"),
            )
        }
        3 => {
            // HTML linked formatter (uses CSS classes, doesn't support themes directly)
            Box::new(
                HtmlLinkedBuilder::new()
                    .lang(language)
                    .build()
                    .expect("Failed to build HTML linked formatter"),
            )
        }
        _ => {
            let error_msg = format!("error: invalid formatter type: {}. Valid types are: 0 (Terminal), 1 (HTML inline), 2 (HTML linked)", formatter_type);
            eprintln!("{}", error_msg);
            panic!("{}", error_msg);
        }
    }
}

fn leak_string(s: String) -> i64 {
    let bytes = s.as_bytes();
    let len = bytes.len();

    unsafe {
        let ptr = wasm_malloc(len);

        if ptr.is_null() {
            eprintln!("error: allocation failed");
            panic!("error: allocation failed");
        }

        ptr::copy_nonoverlapping(bytes.as_ptr(), ptr, len);

        (((ptr as u32 as u64) << 32) | (len as u32 as u64)) as i64
    }
}

fn read_string(ptr: i32, len: i32) -> String {
    unsafe {
        let bytes = std::slice::from_raw_parts(ptr as *const u8, len as usize);
        std::str::from_utf8(bytes)
            .expect("Invalid UTF-8")
            .to_string()
    }
}

fn consume_string(ptr: i32, len: i32) -> String {
    let result = read_string(ptr, len);
    wasm_free(ptr as *mut u8);
    result
}

// Wizer configuration
#[export_name = "wizer.initialize"]
pub extern "C" fn init() {
    // Preload all languages and formatters to ensure they're initialized
    let theme = themes::get("github_dark").unwrap();

    for language in Language::iter() {
        // Terminal formatter
        if let Ok(formatter) = TerminalBuilder::new()
            .lang(language)
            .theme(Some(theme.clone()))
            .build()
        {
            lumis::highlight("", formatter);
        }
        
        // HTML inline formatter
        if let Ok(formatter) = HtmlInlineBuilder::new()
            .lang(language)
            .theme(Some(theme.clone()))
            .build()
        {
            lumis::highlight("", formatter);
        }
        
        // HTML linked formatter (doesn't support themes directly)
        if let Ok(formatter) = HtmlLinkedBuilder::new()
            .lang(language)
            .build()
        {
            lumis::highlight("", formatter);
        }
    }
}
