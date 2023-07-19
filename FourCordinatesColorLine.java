import java.awt.*;
import java.awt.event.*;

class FourCordinatesColorLine extends Frame implements MouseMotionListener{

 public FourCordinatesColorLine(){

	setSize(900,800);
	addMouseMotionListener(this);
	setVisible(true);
}

public void paint(Graphics g)
{
}
public void mouseMoved(MouseEvent e1){
	int x,y;
	x=e1.getX();
	y=e1.getY();
	Graphics g = getGraphics();
	g.setColor(Color.green);
	g.drawLine(0,0,x,y);
	g.setColor(Color.orange);
	g.drawLine(700,0,x,y);
	g.setColor(Color.blue);
	g.drawLine(0,900,x,y);
	g.setColor(Color.red);
	g.drawLine(700,900,x,y);


}
public void mouseDragged(MouseEvent e1){
	

}

public static void main(String [] args){

FourCordinatesColorLine d = new FourCordinatesColorLine();

}}