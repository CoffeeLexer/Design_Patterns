package network;

import network.data.Message;

import java.io.Serializable;

public class Test implements Serializable {
    public int[] array;
    public Message[] messages;
    public int a;

    public static void main(String[] args) {
        Test a = new Test();
        a.a = 1;
        Test b = a;
        b.a = 2;
        System.out.println(a.a);
    }
}
