package com.hlw.java_base.high_concurrency.base;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @version 1.0.0
 * @classname: TestPark
 * @author: LongWang·Hou
 * @description: LockSupport
 * @date: 2021/7/14 0014 15:20
 * @modified: LongWang·Hou
 */
public class TestPark {

    public void testPark(){
        LockSupport.park();
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList(
                "http://172.16.2.204:6713/mag/hls/ae5cae650743409a99aff6d54670eefd/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/9bdd80c97e274d2bb187a3fb1cc3106c/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/dc99676cbd5e4ac5bbeae206e9600490/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/de572557e56e4d208fb89aed059b1980/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/ce19d2ff54c643c7b681c9e583ae008f/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/ec26ff4cdf824e9097d172951834a2a2/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/ebb8abccf1d54a85ab3046ffb506a5af/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/a8d7907b0c564416832fbe92fe9b8493/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/c9e1122316c14ef7a23e04db876e7a3e/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/2f7db3e7fc2d4200b3319c8bbfbee459/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/39e934ab36064fc6b9c08aa11e33bbda/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/3c2f0334f3644e118be7fbbff86091cd/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/ed63fabf94484042b470485dac7f28f4/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/2c6d686f56ef434db0782adfbeccfc67/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/debe3cbd96d849c591f756af53cf0778/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/ab65c4aa023747fca24db6e77d18d4fa/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/16776af93e714b3a9703f50531392d73/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/8d9ced471aa24e98839355579ce1e45b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/1586018807454101b36cc42effb6893a/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/a573f2e640a4424da58bc7526fd82e49/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/b5ad8e21abdb46f6aa40fa308ff43f25/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/40f57b9238484988b9b1cd906bd79216/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/9ad2ad0c559845309ab27dd1e629c8bc/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/a5a11d049ae14fa0a757cda48b38ab04/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/358af97b5cb1475dabe3769f906eef6d/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/344e44bc39dd4cddbd434aa38b298217/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/b981e8b41ed34c37a18b3cae8b7322f6/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/9e1184d8cd9f42bca712784f878654d8/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/86e37b7c41be4566ae4c4bf6d317abea/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/3cd0ba88862348eca876214ac80ea08d/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/35f423ba22684e97afc50116ea2fc733/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/6f103441680b4233889f6266af75f66b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/fd1ae75964cf47a8b8017b2071e039e2/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/67edc5383e0e4e5887f0225789f87862/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/9fa24e39faf64ed4905859e1e668d681/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/0c41a3c88100475d936507a146c1421d/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/698bf0b0f19346229448f6579c0d6d06/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/80568d19132e42e28fb366a9f538fff6/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/b4762a446dac457b8434858304225ea9/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/26b81eeb585a410192b5aeb34c90c458/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/37f9cdae469147819bfca2af03972954/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/894a277e94674839a5e3ccad494df726/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/26caea7f6bec4039b7381746fec2af17/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/3202cb0fd8fc412bb47f53dd1b75949b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/f87357479de84b0bb53f627bf4e9f90d/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/04b454c66c764b8d9f412af825405683/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/8d4990bbe57240fea0a19ce81fface74/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/6925e48635f1439184621a362faae5ed/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/5ec01157457b437c9a98471ea63471d6/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/6ce44f2675ec4f879f18174dcfce5aea/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/56281d504c204f0e863e02bf25b2bca7/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/69c1fab4e91c4397a425065cd5b6018c/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/36e9239e259847f0857b70efc8d5873b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/bc39db11551644a491eae6972e6c7d3b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/a238e02f0c3e44bb88acfe910beff67a/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/eed1d49dd87f414fb722cda6ccba7e3d/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/5d90678929374bb2adde3395c77a02ca/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/101dfb60bfee4320b45250691441a01b/1/live.m3u8",
                "http://172.16.2.204:6713/mag/hls/8ed0eb0da5114263af475ad56a35859b/1/live.m3u8");

        list.forEach(item->{
            String s = item.replace("/1/", "/0/");
            System.out.println(s);
        });

//        TestPark testPark = new TestPark();
//        testPark.testPark();
//        Thread thread = new Thread(() -> {
//            System.out.println("start");
//         while (!Thread.currentThread().isInterrupted()){
//                LockSupport.park();
//
//            }
//            System.out.println("demo");
//        });
//        thread.start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        LockSupport.unpark(Thread.currentThread());
//        thread.interrupt();
//        System.out.println("end");
    }
}
