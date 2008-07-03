package org.carrot2.workbench.core.helpers;

import org.carrot2.workbench.core.WorkbenchCorePlugin;
import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

/*
 * 
 */
public final class Utils
{
    private Utils()
    {
        // no instances.
    }

    /**
     * Shows dialog with error, message will be taken from status.
     * 
     * @param status
     */
    public static void showError(final IStatus status)
    {
        showError(status.getMessage(), status);
    }

    /**
     * Shows dialog with error.
     * 
     * @param message
     * @param status
     */
    public static void showError(final String message, final IStatus status)
    {
        WorkbenchCorePlugin.getDefault().getLog().log(status);

        if (Display.getCurrent() != null)
        {
            ErrorDialog.openError(Display.getDefault().getActiveShell(), null, message,
                status);
        }
        else
        {
            Display.getDefault().asyncExec(new Runnable()
            {
                public void run()
                {
                    ErrorDialog.openError(Display.getDefault().getActiveShell(), null,
                        message, status);
                }
            });
        }
    }

    /*
     * 
     */
    public static void logError(String message, Throwable exception, boolean showError)
    {
        IStatus status = new OperationStatus(IStatus.ERROR,
            WorkbenchCorePlugin.PLUGIN_ID, -1, message, exception);
        WorkbenchCorePlugin.getDefault().getLog().log(status);
        if (showError)
        {
            showError(status);
        }
    }

    /*
     * 
     */
    public static void logError(Throwable exception, boolean showError)
    {
        logError(exception.getMessage(), exception, showError);
    }

    /**
     * Utility method, the same as <code>Display.getDefault().asyncExec(runnable);</code>
     * 
     * @param runnable
     */
    public static void asyncExec(Runnable runnable)
    {
        Display.getDefault().asyncExec(runnable);
    }

    /**
     * Returns active editor part or <code>null</code> if not found.
     */
    public static IEditorPart getActiveEditor()
    {
        final IWorkbenchWindow wb = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (wb == null) return null;

        final IWorkbenchPage page = wb.getActivePage();
        if (page == null) return null;

        final IEditorPart editor = page.getActiveEditor();
        return editor;
    }

    /**
     * Calls {@link FormToolkit#adapt(Control, boolean, boolean)} for given control. If
     * <code>control</code> is an instance of {@link Composite}, this method is called
     * recursively for all the children.
     */
    public static void adaptToFormUI(FormToolkit toolkit, Control control)
    {
        if (control instanceof Composite)
        {
            final Composite c = (Composite) control;
            toolkit.adapt(c);

            final Control [] children = c.getChildren();
            for (int i = 0; i < children.length; i++)
            {
                adaptToFormUI(toolkit, children[i]);
            }
        }
        else
        {
            toolkit.adapt(control, true, true);
        }
    }
}
