package com.android.launcher4.ui;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.android.launcher4.ItemInfo;
import com.android.launcher4.Launcher;
import com.android.launcher4.LauncherAppWidgetInfo;
import com.android.launcher4.LauncherAppWidgetProviderInfo;
import com.android.launcher4.Workspace.ItemOperator;
import com.android.launcher4.util.Condition;
import com.android.launcher4.util.Wait;
import com.android.launcher4.widget.WidgetCell;

/**
 * Test to add widget from widget tray
 */
@LargeTest
public class AddWidgetTest extends LauncherInstrumentationTestCase {

    private LauncherAppWidgetProviderInfo widgetInfo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        widgetInfo = findWidgetProvider(false /* hasConfigureScreen */);
    }

    public void testDragIcon_portrait() throws Throwable {
        lockRotation(true);
        performTest();
    }

    public void testDragIcon_landscape() throws Throwable {
        lockRotation(false);
        performTest();
    }

    private void performTest() throws Throwable {
        clearHomescreen();
        Launcher launcher = startLauncher();

        // Open widget tray and wait for load complete.
        final UiObject2 widgetContainer = openWidgetsTray();
        assertTrue(Wait.atMost(Condition.minChildCount(widgetContainer, 2), DEFAULT_UI_TIMEOUT));

        // Drag widget to homescreen
        UiObject2 widget = scrollAndFind(widgetContainer, By.clazz(WidgetCell.class)
                .hasDescendant(By.text(widgetInfo.getLabel(mTargetContext.getPackageManager()))));
        dragToWorkspace(widget);

        assertNotNull(launcher.getWorkspace().getFirstMatch(new ItemOperator() {
            @Override
            public boolean evaluate(ItemInfo info, View view) {
                return info instanceof LauncherAppWidgetInfo &&
                        ((LauncherAppWidgetInfo) info).providerName.equals(widgetInfo.provider);
            }
        }));
    }
}
