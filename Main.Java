import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    Color pink = new Color(255, 161, 161);
    Color green = new Color(188, 226, 158);
    Color doneColor = new Color(233, 119, 119);

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 40));
        this.setBackground(pink);
        this.setLayout(new BorderLayout(10, 10));

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(40, 30));
        index.setHorizontalAlignment(JLabel.CENTER);
        index.setFont(new Font("Sans-serif", Font.BOLD, 14));
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write something..");
        taskName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        taskName.setBackground(pink);
        taskName.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(80, 30));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);
        done.setFocusPainted(false);
        this.add(done, BorderLayout.EAST);
    }

    public void changeIndex(int num) {
        this.index.setText(num + ".");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(green);
        taskName.setBackground(green);
        checked = true;
        revalidate();
    }
}

class TaskList extends JPanel {

    Color lightColor = new Color(252, 221, 176);

    TaskList() {
        this.setLayout(new GridLayout(0, 1, 5, 5));
        this.setBackground(lightColor);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();
        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }
    }

    public void removeCompletedTasks() {
        Component[] items = this.getComponents();
        for (int i = items.length - 1; i >= 0; i--) {
            if (items[i] instanceof Task) {
                if (((Task) items[i]).getState()) {
                    remove(items[i]);
                }
            }
        }
        updateNumbers();
        revalidate();
        repaint();
    }
}

class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Color orange = new Color(233, 133, 128);
    Color lightColor = new Color(252, 221, 176);
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(lightColor);

        addTask = new JButton("Add Task");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-serif", Font.BOLD, 18));
        addTask.setBackground(orange);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20));

        clear = new JButton("Clear Finished");
        clear.setBorder(emptyBorder);
        clear.setFont(new Font("Sans-serif", Font.BOLD, 18));
        clear.setBackground(orange);
        this.add(clear);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {

    Color lightColor = new Color(252, 221, 176);

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(lightColor);
        JLabel titleText = new JLabel("To Do List");
        titleText.setPreferredSize(new Dimension(300, 60));
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 28));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class AppFrame extends JFrame {

    private TitleBar title;
    private Footer footer;
    private TaskList list;

    private JButton newTask;
    private JButton clear;

    AppFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        title = new TitleBar();
        footer = new Footer();
        list = new TaskList();

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListeners();
        this.setVisible(true);
    }

    public void addListeners() {

        newTask.addActionListener(e -> {
            Task task = new Task();
            list.add(task);
            list.updateNumbers();

            task.getDone().addActionListener(ev -> {
                task.changeState();
            });

            list.revalidate();
            list.repaint();
        });

        clear.addActionListener(e -> {
            list.removeCompletedTasks();
        });
    }
}

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppFrame());
    }
}
