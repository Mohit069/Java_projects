import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.*;

public class RGBGetter extends WindowAdapter implements AdjustmentListener  {
    Frame f;
    TextField t1;TextField t2; TextField t3; Scrollbar sb1;Scrollbar sb2; Scrollbar sb3 ; Panel p; Label l1;

    Label l2; Label l3;
    int rvalue; int gvalue; int bvalue;
    Color myChoice;
    public void windowClosing(WindowEvent e){
        Window w = e.getWindow();
        w.setVisible(false);
        w.dispose();
        System.exit(1);
    }
    public RGBGetter(){
        f = new Frame();
        f.setSize(300,400);
        f.addWindowListener(this);
        //  f.setLayout(new GridLayout(2,2));
        sb1 = new Scrollbar(Scrollbar.VERTICAL,0,5,0,260);
        sb2 = new Scrollbar(Scrollbar.VERTICAL,0,5,0,260);
        sb3 = new Scrollbar(Scrollbar.VERTICAL,0,5,0,260);

        sb1.addAdjustmentListener(this);
        sb2.addAdjustmentListener(this);
        sb3.addAdjustmentListener(this);
        l1= new Label("Red");
        l2= new Label("Green");
        l3= new Label("Blue");
        t1 = new TextField(20);
        t2= new TextField(20);
        t3= new TextField(20);
        p= new Panel();
        p.setLayout(new GridLayout(1,0));

        p.add(t1);
        p.add(l1);

        p.add(t2);
        p.add(l2);

        p.add(t3);
        p.add(l3);

        f.add(p,"South");
        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(0,3));
        p1.add(sb1);
        p1.add(sb2);
        p1.add(sb3);

        f.add(p1,"East");

        f.setVisible(true);
    }
    public void adjustmentValueChanged(AdjustmentEvent e){
        rvalue=sb1.getValue();

        t1.setText(rvalue+" ");

        gvalue=sb2.getValue();
        t2.setText(gvalue + " ");

        bvalue=sb3.getValue();
        t3.setText(bvalue+" ");

        myChoice = new Color(rvalue, gvalue, bvalue);
        f.setBackground(myChoice);

    }

    public static void main(String[] args) {
        RGBGetter d = new RGBGetter();
    }
}

