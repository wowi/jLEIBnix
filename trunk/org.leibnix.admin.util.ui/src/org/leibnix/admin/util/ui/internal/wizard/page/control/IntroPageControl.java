package org.leibnix.admin.util.ui.internal.wizard.page.control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskTypeWizardPage;

public class IntroPageControl extends Composite {

	private Label label = null;
	private Composite composite1 = null;
	private Label label2 = null;
	private Text text = null;
	private Label tasklabel = null;
	private Button radioButtondayly = null;
	private Button radioButtonweekly = null;
	private Button radioButtonmonthly = null;
	private Button radioButtonOnce = null;
	private NewTimerTaskTypeWizardPage mPage;

	public IntroPageControl(NewTimerTaskTypeWizardPage page, Composite parent,
			int style) {
		super(parent, style);
		mPage = page;
		initialize();
	}

	private void initialize() {
		RowLayout rowLayout = new RowLayout();
		rowLayout.fill = true;
		this.setLayout(rowLayout);
		createComposite1();
		setSize(new Point(400, 200));
	}

	/**
	 * This method initializes composite1
	 * 
	 */
	private void createComposite1() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		RowData rowData1 = new org.eclipse.swt.layout.RowData();
		rowData1.height = 200;
		rowData1.width = 400;
		composite1 = new Composite(this, SWT.NONE);
		composite1.setLayoutData(rowData1);
		composite1.setLayout(gridLayout);
		label2 = new Label(composite1, SWT.NONE);
		label2.setText("Bitte geben Sie einen Tasknamen ein.");
		label2.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		text = new Text(composite1, SWT.BORDER);
		if (mPage.getId() != null) {
			text.setText(mPage.getId());
		}
		text.setLayoutData(gridData);
		text.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				if (text.getText() != null && !text.getText().equals("")) {
					mPage.mId = text.getText();
					mPage.setPageComplete(true);
				} else {
					mPage.setPageComplete(false);
				}
			}
		});
		tasklabel = new Label(composite1, SWT.NONE);
		tasklabel.setText("Task ausführen");
		tasklabel.setFont(new Font(Display.getDefault(), "Arial", 10,
				SWT.NORMAL));
		radioButtondayly = new Button(composite1, SWT.RADIO);
		radioButtondayly.setText("Täglich");
		radioButtondayly.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mPage.mType = 1;
			}

		});

		radioButtonweekly = new Button(composite1, SWT.RADIO);
		radioButtonweekly.setText("Wöchentlich");
		if (mPage.getType() == 2) {
			radioButtonweekly.setSelection(true);
		}
		radioButtonweekly.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mPage.mType = 2;
			}

		});

		radioButtonmonthly = new Button(composite1, SWT.RADIO);
		radioButtonmonthly.setText("Monatlich");
		radioButtonmonthly.setEnabled(false);
		radioButtonOnce = new Button(composite1, SWT.RADIO);
		radioButtonOnce.setText("Einmalig");
		radioButtonOnce.setEnabled(false);
	}

}
