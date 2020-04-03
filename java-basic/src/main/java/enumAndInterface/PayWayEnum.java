package enumAndInterface;

public enum PayWayEnum implements PayWay{
    CASH("Cash","现金"){
        @Override
        public void pay() {
            System.out.println("现金支付");
        }
    },
    WECHAT("Wechat","微信支付"){
        @Override
        public void pay() {
            System.out.println("微信支付");
        }
    };

    PayWayEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;

    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
