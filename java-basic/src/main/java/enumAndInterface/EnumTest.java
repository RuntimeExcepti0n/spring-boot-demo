package enumAndInterface;

import org.junit.Test;

public class EnumTest {


    @Test
    public void enumTest(){
        PayWayEnum.CASH.pay();
//        System.out.println();
        PayWayEnum[] values = PayWayEnum.values();
        for (PayWayEnum value : values){
//            if(value)
        }
    }
}
