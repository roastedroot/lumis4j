package io.roastedroot.lumis4j.core;

import java.util.HashMap;
import java.util.Map;

public enum Theme {
    AURA_DARK("aura_dark"),
    AURA_DARK_SOFT_TEXT("aura_dark_soft_text"),
    AURA_SOFT_DARK("aura_soft_dark"),
    AURA_SOFT_DARK_SOFT_TEXT("aura_soft_dark_soft_text"),
    AYU_DARK("ayu_dark"),
    AYU_LIGHT("ayu_light"),
    AYU_MIRAGE("ayu_mirage"),
    BAMBOO_LIGHT("bamboo_light"),
    BAMBOO_MULTIPLEX("bamboo_multiplex"),
    BAMBOO_VULGARIS("bamboo_vulgaris"),
    BLULOCO_DARK("bluloco_dark"),
    BLULOCO_LIGHT("bluloco_light"),
    CARBONFOX("carbonfox"),
    CATPPUCCIN_FRAPPE("catppuccin_frappe"),
    CATPPUCCIN_LATTE("catppuccin_latte"),
    CATPPUCCIN_MACCHIATO("catppuccin_macchiato"),
    CATPPUCCIN_MOCHA("catppuccin_mocha"),
    CYBERDREAM_DARK("cyberdream_dark"),
    CYBERDREAM_LIGHT("cyberdream_light"),
    DARKPLUS("darkplus"),
    DAWNFOX("dawnfox"),
    DAYFOX("dayfox"),
    DRACULA("dracula"),
    DRACULA_SOFT("dracula_soft"),
    DUSKFOX("duskfox"),
    EDGE_AURA("edge_aura"),
    EDGE_DARK("edge_dark"),
    EDGE_LIGHT("edge_light"),
    EDGE_NEON("edge_neon"),
    EVERFOREST_DARK("everforest_dark"),
    EVERFOREST_LIGHT("everforest_light"),
    FLEXOKI_DARK("flexoki_dark"),
    FLEXOKI_LIGHT("flexoki_light"),
    GITHUB_DARK("github_dark"),
    GITHUB_DARK_COLORBLIND("github_dark_colorblind"),
    GITHUB_DARK_DIMMED("github_dark_dimmed"),
    GITHUB_DARK_HIGH_CONTRAST("github_dark_high_contrast"),
    GITHUB_DARK_TRITANOPIA("github_dark_tritanopia"),
    GITHUB_LIGHT("github_light"),
    GITHUB_LIGHT_COLORBLIND("github_light_colorblind"),
    GITHUB_LIGHT_HIGH_CONTRAST("github_light_high_contrast"),
    GITHUB_LIGHT_TRITANOPIA("github_light_tritanopia"),
    GRUVBOX_DARK("gruvbox_dark"),
    GRUVBOX_DARK_HARD("gruvbox_dark_hard"),
    GRUVBOX_DARK_SOFT("gruvbox_dark_soft"),
    GRUVBOX_LIGHT("gruvbox_light"),
    GRUVBOX_LIGHT_HARD("gruvbox_light_hard"),
    GRUVBOX_LIGHT_SOFT("gruvbox_light_soft"),
    HORIZON_DARK("horizon_dark"),
    ICEBERG("iceberg"),
    KANAGAWA_DRAGON("kanagawa_dragon"),
    KANAGAWA_LOTUS("kanagawa_lotus"),
    KANAGAWA_WAVE("kanagawa_wave"),
    MATERIAL_DARKER("material_darker"),
    MATERIAL_DEEP_OCEAN("material_deep_ocean"),
    MATERIAL_LIGHTER("material_lighter"),
    MATERIAL_OCEANIC("material_oceanic"),
    MATERIAL_PALENIGHT("material_palenight"),
    MATTE_BLACK("matte_black"),
    MELANGE_DARK("melange_dark"),
    MELANGE_LIGHT("melange_light"),
    MODUS_OPERANDI("modus_operandi"),
    MODUS_VIVENDI("modus_vivendi"),
    MOLOKAI("molokai"),
    MONOKAI_PRO_DARK("monokai_pro_dark"),
    MONOKAI_PRO_MACHINE("monokai_pro_machine"),
    MONOKAI_PRO_RISTRETTO("monokai_pro_ristretto"),
    MONOKAI_PRO_SPECTRUM("monokai_pro_spectrum"),
    MOONFLY("moonfly"),
    MOONLIGHT("moonlight"),
    NEOSOLARIZED_DARK("neosolarized_dark"),
    NEOSOLARIZED_LIGHT("neosolarized_light"),
    NEOVIM_DARK("neovim_dark"),
    NEOVIM_LIGHT("neovim_light"),
    NIGHTFLY("nightfly"),
    NIGHTFOX("nightfox"),
    NORD("nord"),
    NORDFOX("nordfox"),
    NORDIC("nordic"),
    ONEDARK("onedark"),
    ONEDARK_COOL("onedark_cool"),
    ONEDARK_DARKER("onedark_darker"),
    ONEDARK_DEEP("onedark_deep"),
    ONEDARK_LIGHT("onedark_light"),
    ONEDARK_WARM("onedark_warm"),
    ONEDARK_WARMER("onedark_warmer"),
    ONEDARKPRO_DARK("onedarkpro_dark"),
    ONEDARKPRO_VIVID("onedarkpro_vivid"),
    ONELIGHT("onelight"),
    PAPERCOLOR_DARK("papercolor_dark"),
    PAPERCOLOR_LIGHT("papercolor_light"),
    ROSEPINE_DARK("rosepine_dark"),
    ROSEPINE_DAWN("rosepine_dawn"),
    ROSEPINE_MOON("rosepine_moon"),
    SOLARIZED_AUTUMN_DARK("solarized_autumn_dark"),
    SOLARIZED_AUTUMN_LIGHT("solarized_autumn_light"),
    SOLARIZED_SPRING_DARK("solarized_spring_dark"),
    SOLARIZED_SPRING_LIGHT("solarized_spring_light"),
    SOLARIZED_SUMMER_DARK("solarized_summer_dark"),
    SOLARIZED_SUMMER_LIGHT("solarized_summer_light"),
    SOLARIZED_WINTER_DARK("solarized_winter_dark"),
    SOLARIZED_WINTER_LIGHT("solarized_winter_light"),
    SRCERY("srcery"),
    TERAFOX("terafox"),
    TOKYONIGHT_DAY("tokyonight_day"),
    TOKYONIGHT_MOON("tokyonight_moon"),
    TOKYONIGHT_NIGHT("tokyonight_night"),
    TOKYONIGHT_STORM("tokyonight_storm"),
    VSCODE_DARK("vscode_dark"),
    VSCODE_LIGHT("vscode_light"),
    XCODE_DARK("xcode_dark"),
    XCODE_DARK_HC("xcode_dark_hc"),
    XCODE_LIGHT("xcode_light"),
    XCODE_LIGHT_HC("xcode_light_hc"),
    XCODE_WWDC("xcode_wwdc"),
    ZENBURN("zenburn"),
    ZEPHYR_DARK("zephyr_dark");

    private final String value;

    Theme(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    private static final Map<String, Theme> themes;

    static {
        themes = new HashMap<>();
        for (Theme theme : Theme.values()) {
            themes.put(theme.value(), theme);
        }
    }

    public static Theme fromString(String theme) {
        if (!themes.containsKey(theme)) {
            throw new IllegalArgumentException("Theme '" + theme + "' not found.");
        }
        return themes.get(theme);
    }
}
