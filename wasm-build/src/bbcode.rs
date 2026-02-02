//! BBCode formatter for syntax highlighting.
//!
//! This module provides the [`BBCode`] formatter that generates BBCode output with
//! scope-based tags (e.g., `[keyword-function]text[/keyword-function]`) matching the
//! format produced by converting HTML-linked output to BBCode.

use lumis::{formatter::Formatter, highlight, languages::Language, themes::Theme};
use std::io::{self, Write};

/// BBCode formatter for syntax highlighting with BBCode tags.
///
/// Generates BBCode output using scope-based tags, similar to HTML-linked formatter
/// converted to BBCode (e.g., `[keyword-function]fn[/keyword-function]`).
pub struct BBCode {
    lang: Language,
    theme: Option<Theme>,
}

impl BBCode {
    pub fn new(lang: Language, theme: Option<Theme>) -> Self {
        Self { lang, theme }
    }
}

/// Convert scope name to BBCode tag name.
///
/// Converts tree-sitter scope names (dot-separated) to BBCode tag names
/// (hyphen-separated), matching the format used by HTML-linked formatter.
///
/// # Arguments
///
/// * `scope` - The tree-sitter scope name (e.g., "keyword.function")
///
/// # Returns
///
/// BBCode tag name (e.g., "keyword-function")
fn scope_to_tag_name(scope: &str) -> String {
    // Convert dots to hyphens to match HTML-linked CSS class format
    scope.replace('.', "-")
}

/// Wrap text with BBCode tags based on scope.
///
/// # Arguments
///
/// * `text` - The text to wrap
/// * `scope` - The tree-sitter scope name
///
/// # Returns
///
/// Text wrapped with BBCode tags, or plain text if scope is empty.
fn wrap_with_bbcode(text: &str, scope: &str) -> String {
    if scope.is_empty() {
        return text.to_string();
    }
    
    let tag_name = scope_to_tag_name(scope);
    format!("[{}]{}[/{}]", tag_name, text, tag_name)
}

impl Formatter for BBCode {
    fn format(&self, source: &str, output: &mut dyn Write) -> io::Result<()> {
        highlight::highlight_iter(
            source,
            self.lang,
            self.theme.clone(),
            |text, _range, scope, _style| {
                let bbcode_text = wrap_with_bbcode(text, scope);
                write!(output, "{}", bbcode_text)
            },
        )
        .map_err(io::Error::other)
    }
}
