package org.leibnix.admin.util.ui.internal.wizard.page.control;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.nebula.widgets.cdatetime.CDT;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Button;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskWeeklyWizardPage;

public class WeeklyPageControl extends Composite {

	private Composite composite = null;
	private Label labelStartTime = null;
	private CDateTime cdt=null;
	private Composite composite1 = null;
	private Label label = null;
	private Spinner spinner = null;
	private Label label1 = null;
	private Label label2 = null;
	private Composite composite2 = null;
	private Button checkBox = null;
	private Button checkBox1 = null;
	private Button checkBox2 = null;
	private Button checkBox3 = null;
	private Button checkBox4 = null;
	private Button checkBox5 = null;
	private Button checkBox6 = null;
	private Button checkBox61 = null;
	private NewTimerTaskWeeklyWizardPage mPage;

	public WeeklyPageControl(NewTimerTaskWeeklyWizardPage pPage, Composite parent, int style) {
		super(parent, style);
		initialize();
		mPage = pPage;
	}

	private void initialize() {
		RowLayout rowLayout = new RowLayout();
		rowLayout.fill = true;
		this.setLayout(rowLayout);
		createComposite();
		setSize(new Point(400, 200));
	}

	/**
	 * This method initializes composite	
	 *
	 */
	private void createComposite() {
		RowData rowData = new org.eclipse.swt.layout.RowData();
		rowData.height = 195;
		rowData.width = 395;
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(rowData);
		labelStartTime = new Label(composite, SWT.NONE);
		labelStartTime.setText("Startzeit");
		labelStartTime.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		
		cdt = new CDateTime(composite, CDT.BORDER | CDT.SPINNER);
		cdt.setFormat(CDT.TIME_MEDIUM);	
		cdt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});

		cdt.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		createComposite1();
		label2 = new Label(composite, SWT.NONE);
		label2.setText("Wählen Sie die Wochentage.");
		createComposite2();
	}

	/**
	 * This method initializes composite1	
	 *
	 */
	private void createComposite1() {
		GridData gridData = new GridData();
		gridData.widthHint = 200;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		composite1 = new Composite(composite, SWT.NONE);
		composite1.setLayout(gridLayout);
		composite1.setLayoutData(gridData);
		label = new Label(composite1, SWT.NONE);
		label.setText("Jede ");
		label.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		spinner = new Spinner(composite1, SWT.BORDER);
		spinner.setMinimum(1);
		spinner.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
		spinner.setEnabled(false);
		spinner.setMaximum(52);
		label1 = new Label(composite1, SWT.NONE);
		label1.setText("te Woche");
		label1.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NORMAL));
	}

	/**
	 * This method initializes composite2	
	 *
	 */
	private void createComposite2() {
		GridData gridData2 = new GridData();
		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 2;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		composite2 = new Composite(composite, SWT.NONE);
		composite2.setLayout(gridLayout1);
		composite2.setLayoutData(gridData1);
		checkBox2 = new Button(composite2, SWT.CHECK);
		checkBox2.setText("Montag");
		checkBox2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		checkBox = new Button(composite2, SWT.CHECK);
		checkBox.setText("Donnerstag");
		checkBox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		checkBox1 = new Button(composite2, SWT.CHECK);
		checkBox1.setText("Dienstag");
		checkBox1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		checkBox4 = new Button(composite2, SWT.CHECK);
		checkBox4.setText("Freitag");
		checkBox4.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		checkBox3 = new Button(composite2, SWT.CHECK);
		checkBox3.setText("Mittwoch");
		checkBox3.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		checkBox5 = new Button(composite2, SWT.CHECK);
		checkBox5.setText("Samstag");
		checkBox5.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
		Label filler = new Label(composite2, SWT.NONE);
		checkBox61 = new Button(composite2, SWT.CHECK);
		checkBox61.setText("Sonntag");
		checkBox61.setLayoutData(gridData2);
		checkBox61.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				updateExpression ();
			}
		});
	}

	private void updateExpression() {
		// TODO Auto-generated method stub
		Date date = cdt.getSelection();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		StringBuffer buf = new StringBuffer ();
		buf = buf.append(cal.get(Calendar.SECOND));
		buf = buf.append(" ");
		buf = buf.append(cal.get(Calendar.MINUTE));
		buf = buf.append(" ");
		int hour_offset=0;
		if (cal.get(Calendar.AM_PM) == Calendar.PM) {
			hour_offset=12;
		}
		buf = buf.append(cal.get(Calendar.HOUR)+hour_offset);
		buf = buf.append(" ? * "); // day of month and month
		boolean comma=false;
		if (checkBox61.getSelection()) {
			buf = buf.append("SUN");
			comma=true;
		}
		if (checkBox2.getSelection()) {
			if (comma) {
				buf = buf.append(",MON");
			} else {
				buf = buf.append("MON");
				comma=true;
			}
		}
		if (checkBox1.getSelection()) {
			if (comma) {
				buf = buf.append(",TUE");
			} else {
				buf = buf.append("TUE");
				comma=true;
			}
		}
		if (checkBox3.getSelection()) {
			if (comma) {
				buf = buf.append(",WED");
			} else {
				buf = buf.append("WED");
				comma=true;
			}
		}
		if (checkBox.getSelection()) {
			if (comma) {
				buf = buf.append(",THU");
			} else {
				buf = buf.append("THU");
				comma=true;
			}
		}
		if (checkBox4.getSelection()) {
			if (comma) {
				buf = buf.append(",FRI");
			} else {
				buf = buf.append("FRI");
				comma=true;
			}
		}
		if (checkBox5.getSelection()) {
			if (comma) {
				buf = buf.append(",SAT");
			} else {
				buf = buf.append("SAT");
				comma=true;
			}
		}
		mPage.mExpression = buf.toString();
	}

	public void setExpression(String pExpression) {
		String[] items = pExpression.split (" ");
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(),Locale.getDefault());
		cal.set(Calendar.SECOND,Integer.parseInt(items[0]));
		cal.set(Calendar.MINUTE,Integer.parseInt(items[1]));
		int hour = Integer.parseInt(items[2]);
		if (hour > 12) {
			hour = hour - 12;
		}
		cal.set(Calendar.HOUR,hour);
		if (Integer.parseInt(items[2])>12) {
			cal.set(Calendar.AM_PM, Calendar.PM);
		} else {
			cal.set(Calendar.AM_PM, Calendar.AM);			
		}	
		cdt.setSelection(cal.getTime());
		String[] dayItems = items[5].split(",");
		for (int i=0; i<dayItems.length;i++) {
			if (dayItems[i].equals("SUN")) {
				checkBox61.setSelection(true);
			} else if (dayItems[i].equals("MON")) {
				checkBox2.setSelection(true);
			} else if (dayItems[i].equals("TUE")) {
				checkBox1.setSelection(true);
			} else if (dayItems[i].equals("WED")) {
				checkBox3.setSelection(true);
			} else if (dayItems[i].equals("THU")) {
				checkBox.setSelection(true);
			} else if (dayItems[i].equals("FRI")) {
				checkBox4.setSelection(true);
			} else if (dayItems[i].equals("SAT")) {
				checkBox5.setSelection(true);
			}
		}
	}

}
