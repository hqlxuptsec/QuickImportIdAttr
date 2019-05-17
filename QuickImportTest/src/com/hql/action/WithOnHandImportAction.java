package com.hql.action;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.hql.ui.FilePathImportDialog;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;



public class WithOnHandImportAction extends AnAction {
    private String layoutPath;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        if (editor.getSelectionModel().getSelectedText().isEmpty()) {
            showNotification(e, "pleaseSelectXml", "请选中xml文件", "");
            return;
        }
        String basePath = editor.getProject().getBasePath();
        basePath = basePath.replace("/", "\\");
        layoutPath = new StringBuilder().append(basePath).append("\\app\\src\\main\\res\\layout\\").append(editor.getSelectionModel().getSelectedText()).append(".xml").toString();

        FilePathImportDialog dialog = new FilePathImportDialog(e, null);
        dialog.setTvPathText(layoutPath);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showNotification(@NotNull AnActionEvent e, String displayId, String title, String message) {
        NotificationGroup notificationGroup = new NotificationGroup(displayId, NotificationDisplayType.BALLOON, true);
        notificationGroup.createNotification(
                title,
                message,
                NotificationType.INFORMATION,
                null
        ).notify(e.getProject());
    }
}
