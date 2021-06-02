import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import java.util.*;
import java.awt.*;

public class Proj03Runner {

    public Proj03Runner() {
        System.out.println(
                "I certify that this program is my own work\nand is not the work of others. I agree not\nto share my solution withothers.\nNicholas Jocius\n");
    }// end Proj03Runner constructor

    void run(String input) {
        try {
            new Proj03RunnerHtmlHandler(input);
        } catch (Exception e) {
            e.printStackTrace();
        } // end catch

    }// end run()

}// end class Proj03Runner

class Proj03RunnerHtmlHandler extends JFrame implements HyperlinkListener {

    JEditorPane html;
    JButton back;
    JButton forward;
    JTextField txtField;
    JPanel buttons;
    ArrayList<String> backList = new ArrayList<>();
    ArrayList<String> forwardList = new ArrayList<>();
    String currentUrl;

    public Proj03RunnerHtmlHandler(String website) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Nicholas Jocius");

        try {
            if (website != null) {
                html = new JEditorPane(website);
                html.setEditable(false);
                html.addHyperlinkListener(this);

                buttons = new JPanel();
                back = new JButton("<");
                back.setEnabled(false);
                back.addActionListener(e -> actionBack());
                forward = new JButton(">");
                forward.setEnabled(false);
                forward.addActionListener(e -> actionForward());

                txtField = new JTextField(35);
                txtField.addActionListener(e -> actionGo());
                txtField.setText(website);

                buttons.add(back);
                buttons.add(txtField);
                buttons.add(forward);

                JScrollPane scroller = new JScrollPane();
                JViewport vp = scroller.getViewport();
                vp.add(html);

                this.setLayout(new BorderLayout());
                this.getContentPane().add(scroller, BorderLayout.CENTER);
                this.getContentPane().add(buttons, BorderLayout.NORTH);
                this.setSize(669, 669);
                this.setVisible(true);
                this.currentUrl = website;
            } // end if

        } catch (Exception e) {
            e.printStackTrace();
        } // end catch

    }// end contstructor

    private void actionBack() {

        try {
            if (backList.isEmpty()) {
                return;
            }
            forwardList.add(currentUrl);
            this.currentUrl = backList.get(backList.size() - 1);
            showPage(new URL(backList.get(backList.size() - 1)));
            backList.remove(backList.size() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actionForward() {

        try {
            if (forwardList.isEmpty()) {
                return;
            }
            backList.add(currentUrl);
            this.currentUrl = forwardList.get(forwardList.size() - 1);
            showPage(new URL(forwardList.get(forwardList.size() - 1)));
            forwardList.remove(forwardList.size() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // update current url
    private void updateUrl(URL url) {
        URL currUrl = url;
        txtField.setText(currUrl.toString());
    }

    private void updateButtons(URL url) {
        int currBIndx = backList.indexOf(url.toString());

        if (backList.size() >= 1 && currBIndx != 0) {
            back.setEnabled(true);
        } else {
            back.setEnabled(false);
        }
        if (!forwardList.isEmpty()) {
            forward.setEnabled(true);
        } else {
            forward.setEnabled(false);
        }
    }

    private void actionGo() {
        String url = txtField.getText();
        if (!url.toLowerCase().startsWith("http://")) {
            System.out.println("Invalid Url");
        } else {
            try {
                URL confirmedUrl = new URL(url);
                showPage(confirmedUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void showPage(URL page) {
        try {
            html.setPage(page);

            if (!currentUrl.equals(page.toString())) {
                this.backList.add(this.currentUrl);
            }
            this.currentUrl = page.toString();
            updateUrl(page);
            updateButtons(page);

        } catch (Exception e) {
            e.printStackTrace();
        } // end catch
    }// end showPage()

    public void hyperlinkUpdate(HyperlinkEvent e) {

        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

            if (e instanceof HTMLFrameHyperlinkEvent) {
                System.out.println("Html frame events are ignored");
            } else {
                try {
                    showPage(e.getURL());

                } catch (Exception ex) {
                    ex.printStackTrace();
                } // end catch
            } // end else
        } // end if

    }// end hyperlinkUpdate()

}// end class Proj03RunnerHtmlHandler
