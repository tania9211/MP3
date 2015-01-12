package read.traverse;

import netscape.javascript.JSObject;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by BiliaievaTatiana on 1/12/15.
 */
public class HelloWorld extends Applet implements ActionListener{

    public void init() {
        setLayout(new FlowLayout());
        Button button = new Button("Execute");
        button.addActionListener(this);
        button.setActionCommand("EXECUTE");
        add(button);
    }

    public String getGreeting() {
        return "Get greeting";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("EXECUTE")) {
            JSObject jsObject = JSObject.getWindow(this);
            jsObject.call("callHello", new String [] {"Tanushka"});
        }
    }
}
