package com.example.base;

/**
 * @ClassName: TestConstructor
 * @Description:
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public enum TestEnumImpl implements TestEnum {

    SUCCESS(100, "操作"),
    FAIL("失败");

    private final int code;
    private final String msg;

    TestEnumImpl(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    TestEnumImpl(String msg) {
        this.code = 120;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
