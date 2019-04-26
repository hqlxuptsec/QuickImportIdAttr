package com.hql.action;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.hql.ui.FilePathImportDialog;
import org.jetbrains.annotations.NotNull;



public class WithOnHandImportAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        FilePathImportDialog dialog = new FilePathImportDialog(e, null);
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
