package com.example.base_ui.nice_spinner;

/**
 * @author 53288
 * @description
 * @date 2021/1/20
 */
public enum PopUpTextAlignment {

    START(0),
    END(1),
    CENTER(2);

    private final int id;

    PopUpTextAlignment(int id) {
        this.id = id;
    }

    static PopUpTextAlignment fromId(int id) {
        for (PopUpTextAlignment value : values()) {
            if (value.id == id) return value;
        }
        return CENTER;
    }
}
