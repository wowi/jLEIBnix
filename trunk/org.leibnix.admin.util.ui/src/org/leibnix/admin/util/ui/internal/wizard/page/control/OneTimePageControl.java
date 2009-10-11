package org.leibnix.admin.util.ui.internal.wizard.page.control;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.nebula.widgets.cdatetime.CDT;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskOneTimeWizardPage;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskWeeklyWizardPage;
import org.eclipse.swt.layout.GridData;

public class OneTimePageControl extends Composite {

	private Label labelStartTime = null;
	private CDateTime cdt=null;
	private Label lableStartDate = null;


	public OneTimePageControl(NewTimerTaskOneTimeWizardPage pPage, Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData13 = new GridData();
		gridData13.widthHint = 150;
		GridData gridData12 = new GridData();
		gridData12.widthHint = 150;
		GridData gridData2 = new GridData();
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.grabExcessVerticalSpace = false;
		gridData2.grabExcessHorizontalSpace = true;
		GridData gridData1 = new GridData();
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData1.verticalSpan = 5;
		gridData1.grabExcessHorizontalSpace = true;
		GridData gridData = new GridData();
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData.grabExcessVerticalSpace = false;
		gridData.verticalSpan = 5;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.makeColumnsEqualWidth = false;
		gridLayout.verticalSpacing = 10;
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
		
		labelStartTime = new Label(this, SWT.NONE);
		labelStartTime.setText("Startzeit:");
		labelStartTime.setLayoutData(gridData);
		labelStartTime.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		
		cdt = new CDateTime(this, CDT.BORDER | CDT.SPINNER);
		cdt.setFormat(CDT.TIME_MEDIUM);	
		cdt.setLayoutData(gridData12);
		cdt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				// updateExpression ();
			}
		});

		cdt.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));

		lableStartDate = new Label(this, SWT.NONE);
		lableStartDate.setText("Startdatum:");
		lableStartDate.setLayoutData(gridData1);
		lableStartDate.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));

		cdt = new CDateTime(this, CDT.BORDER | CDT.DROP_DOWN);
		cdt.setFormat(CDT.DATE_LONG);	

		cdt.setLayoutData(gridData13);
		cdt.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"
