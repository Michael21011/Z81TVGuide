package info.z81.z81tvguide;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael_Shapiro on 25.09.2017.
 */

public class InfraManager {
     Context mContext;

    /*   Object irdaService;
        Method irWrite;
        SparseArray<String> irData;
        TextView mFreqsText;
        ConsumerIrManager mCIR;
        ImageButton power;
        SeekBar sBar;

        @Override
        protected void InfraManager() {
            // Be sure to call the super class.
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_layout);
            power = (ImageButton) findViewById(R.id.powerBtn);
            power.setVisibility(View.GONE);
            irData = new SparseArray<String>();
            irData.put(
                    R.id.toggleButton1,
                    hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
            irData.put(
                    R.id.upProg,
                    hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 0015 0015 0040 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
            irData.put(
                    R.id.downBtn,
                    hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
            irData.put(
                    R.id.minusBtn,
                    hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
            irData.put(
                    R.id.plusBtn,
                    hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
            irData.put(
                    R.id.muteBtn,
                    hex2dec("0000 006c 0022 0003 00ab 00aa 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0714 00ab 00aa 0015 0015 0015 0e91"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                irInit4KitKat();
            } else {
                irInit4JellyBean();
            }

        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void irInit4KitKat() {

            // Get a reference to the ConsumerIrManager
            mCIR = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);

        }

        public void irInit4JellyBean() {
            Object irService = this.getSystemService("irda");
            //irService.getClass();
            Class irClass = irService.getClass();
            Class params[] = new Class[1];
            params[0] = String.class;
            try {
                irWrite = irClass.getMethod("write_irsend", params);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public void irSend(View view) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                irSend4Kitkat(view);
            } else {

                irSend4JellyBean(view);
            }
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private void irSend4Kitkat(View view) {

            String data = irData.get(view.getId());
            if (data != null) {
                String values[] = data.split(",");
                int[] pattern = new int[values.length - 1];

                for (int i = 0; i < pattern.length; i++) {
                    pattern[i] = Integer.parseInt(values[i + 1]);
                }

                mCIR.transmit(Integer.parseInt(values[0]), pattern);
            }
        }

        private void irSend4JellyBean(View view) {
            String data = irData.get(view.getId());
            if (data != null) {
                try {
                    irWrite.invoke(irdaService, data);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        protected String hex2dec(String irData) {
            List<String> list = new ArrayList<String>(Arrays.asList(irData
                    .split(" ")));
            list.remove(0); // dummy
            int frequency = Integer.parseInt(list.remove(0), 16); // frequency
            list.remove(0); // seq1
            list.remove(0); // seq2

            for (int i = 0; i < list.size(); i++) {
                list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
            }

            frequency = (int) (1000000 / (frequency * 0.241246));
            list.add(0, Integer.toString(frequency));

            irData = "";
            for (String s : list) {
                irData += s + ",";
            }
            return irData;
        }

    */

    protected String hex2dec(String irData) {
        List<String> list = new ArrayList<String>(Arrays.asList(irData
                .split(" ")));
        list.remove(0); // dummy
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        for (int i = 0; i < list.size(); i++) {
            list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
        }

        frequency = (int) (1000000 / (frequency * 0.241246));
        list.add(0, Integer.toString(frequency));

        irData = "";
        for (String s : list) {
            irData += s + ",";
        }
        return irData;
    }

    protected int[] hex2array(String irData) {
        List<String> list = new ArrayList<String>(Arrays.asList(irData
                .split(" ")));
       // list.remove(0); // dummy
        //int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        //list.remove(0); // seq1
        //list.remove(0); // seq2
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i]=Integer.parseInt(list.get(i), 16);
        }

     //   frequency = (int) (1000000 / (frequency * 0.241246));
     //   list.add(0, Integer.toString(frequency));

        return result;
    }

 public InfraManager(Context mContext)
 {
     this.mContext=mContext;
 }

    private String power_on = "0000 006d 0022 0002 0157 00ac 0015 0016 0015 0016 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0016 0015 0041 0015 0041 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0041 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0041 0015 0041 0015 0016 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0689 0157 0056 0015 0e94";

    @TargetApi(Build.VERSION_CODES.KITKAT)
     public void PressPower()
    {
        final ConsumerIrManager mCIR;
        mCIR = (ConsumerIrManager) mContext.getSystemService(Context.CONSUMER_IR_SERVICE);
        //int[] pattern_off = hex2array(power_on);
        int[] pattern_off = {8918 ,4446, 572, 546 ,572 ,546 ,572, 1638, 572, 546, 572, 546,
                572 ,546 ,572 ,546 ,572 ,546 ,572 ,1638 ,572 ,1638 ,572 ,546 ,572 ,1638 ,572 ,
                1638, 572 ,1638 ,572 ,1638 ,572 ,1638 ,572 ,1638 ,572 ,546 ,572 ,1638 ,572,
                546, 572 ,546, 572 ,546, 572, 1638, 572, 1638, 572, 546, 572, 1638, 572 ,546,
                572, 1638, 572, 1638, 572, 1638, 572, 546, 572, 546, 572, 39598, 8892, 2210, 572, 95186};
        mCIR.transmit(38000,pattern_off);

    }
}
