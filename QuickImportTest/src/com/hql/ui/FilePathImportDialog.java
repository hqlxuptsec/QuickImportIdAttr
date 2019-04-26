package com.hql.ui;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.hql.TableModel.OneHandImportTableModel;
import com.hql.entity.ElementBean;
import com.hql.utils.ParseXmlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilePathImportDialog extends JFrame {

    private AnActionEvent event;
    private JPanel contentPane;

    private JButton btnSearch;
    private JTextField tvPath;

    private JPanel tablePanel;

    private JButton btnOk;
    private JButton btnCancel;
    private JRadioButton btnRadio;

    private JFileChooser jFileChooser = new JFileChooser();
    private JBTable jbTable;

    private List<ElementBean> elementBeanList;

    public FilePathImportDialog(AnActionEvent event, List<ElementBean> data) {

        this.event = event;
        this.elementBeanList = data;

        initUI();
        initListener();
    }

    private void initListener() {
        btnOk.addActionListener(e -> onOK());

        btnCancel.addActionListener(e1 -> onCancel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e1) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e1 -> onCancel()
                , KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
                , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnSearch.addActionListener(e -> {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.showDialog(new JLabel(), "选择");
            File file = jFileChooser.getSelectedFile();
            tvPath.setText(file.getAbsoluteFile().toString());

            elementBeanList = ParseXmlUtil
                    .parseXmlFile(file.getAbsoluteFile().toString());
            setCustomTableModel(new OneHandImportTableModel(elementBeanList));
        });
    }

    private void initUI() {
        setContentPane(contentPane);
        setTitle("快速导入界面UI控件");
        getRootPane().setDefaultButton(btnOk);
        //展示区域
        jbTable = new JBTable(new OneHandImportTableModel(elementBeanList));
        jbTable.setPreferredScrollableViewportSize(new Dimension(800, 300));
        jbTable.setFillsViewportHeight(true);

        JBScrollPane scrollPane = new JBScrollPane(jbTable);
        tablePanel.setLayout(new GridLayout(1, 0));
        tablePanel.add(scrollPane);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void onOK() {
        insetStringAfterOffset(event, elementBeanList);
    }

    private void onCancel() {
        dispose();
    }

    private void setCustomTableModel(OneHandImportTableModel customTableModel) {
        jbTable.setModel(customTableModel);
    }

    private void insetStringAfterOffset(AnActionEvent e, List<ElementBean> data) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();
        WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
            List<String> showStrList = new ArrayList<>();

            for (ElementBean resp : data) {
                if (resp.isSelect()) {
                    if (btnRadio.isSelected()) {
                        String butterKnifeFormat = "\n    @BindView(R.id." + resp.getId() + ")";
                        showStrList.add(butterKnifeFormat);
                    }
                    String impl = "\n    " + resp.getViewName() + "    " /*+ resp.getId()*/ + resp.getAttrsName() + ";";
                    showStrList.add(impl);
                }
            }
            Collections.reverse(showStrList);
            for (String currentStr : showStrList) {
                editor.getDocument().insertString(offset, currentStr);
            }
        });
        showNotification("withTheHandImport", "快速导入界面控件变量", "implementation success");
    }

    private void showNotification(String displayId, String title, String message) {
        NotificationGroup group = new NotificationGroup(displayId, NotificationDisplayType.BALLOON, true);
        group.createNotification(
                title,
                message,
                NotificationType.INFORMATION,
                null
        ).notify(event.getProject());
    }
}
