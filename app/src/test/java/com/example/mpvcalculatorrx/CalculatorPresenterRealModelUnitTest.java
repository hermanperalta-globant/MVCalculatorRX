package com.example.mpvcalculatorrx;

import com.example.mpvcalculatorrx.mvp.model.CalculatorModel;
import com.example.mpvcalculatorrx.mvp.presenter.CalculatorPresenter;
import com.example.mpvcalculatorrx.mvp.view.CalculatorView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorPresenterRealModelUnitTest {

    @Mock
    CalculatorView mockView;
    private CalculatorPresenter presenter;
    private CalculatorModel realModel;

    public static int getDisplayUpdates(Long a, Long b) {
        return (String.valueOf(a) + String.valueOf(b)).length() + 2;
    }

    public static void verifyDisplayUpdatesForOperation(CalculatorView mockView, Long a, Long b) {
        int displayUpdates = CalculatorPresenterRealModelUnitTest.getDisplayUpdates(a, b);
        verify(mockView, times(displayUpdates)).setDisplayValue(anyString());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        realModel = new CalculatorModel();
        presenter = new CalculatorPresenter(realModel, mockView);

        assertNotNull(mockView);
        assertNotNull(realModel);
        assertNotNull(presenter);
    }

    @Test
    public void shouldAddDigit() {
        presenter.onDigitPressed(1);
        verify(mockView).setDisplayValue("1");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldAddManyDigits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            presenter.onDigitPressed(i);
            if (i == 0) {
                verify(mockView).setDisplayValue("");
            } else {
                sb.append(i);
                verify(mockView).setDisplayValue(sb.toString());
            }
        }

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldAddComplementOperatorAtBeginning() {
        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("-");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldAddZeroDigitAtBeginning() {
        presenter.onDigitPressed(0);
        verify(mockView).setDisplayValue("");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldAddAnotherOperatorAtBeginning() {
        presenter.onOperatorPressed('*');
        verify(mockView).setDisplayValue("");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldCalculateAddition() {
        // -10 + -10 = -20
        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("-");
        presenter.onDigitPressed(1);
        verify(mockView).setDisplayValue("-1");
        presenter.onDigitPressed(0);
        verify(mockView).setDisplayValue("-10");

        presenter.onOperatorPressed('+');
        verify(mockView).setDisplayValue("-10+");

        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("-10+-");
        presenter.onDigitPressed(1);
        verify(mockView).setDisplayValue("-10+-1");
        presenter.onDigitPressed(0);
        verify(mockView).setDisplayValue("-10+-10");

        presenter.onEqualsButtonPressed();
        assertEquals(-20, realModel.getResult());
        verify(mockView).setDisplayValue("-20");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldCalculateSubtraction() {
        // 99 - -9 = 108
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("9");
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("99");

        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("99-");

        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("99--");
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("99--9");

        presenter.onEqualsButtonPressed();
        assertEquals(108, realModel.getResult());
        verify(mockView).setDisplayValue("108");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldCalculateMultiplication() {
        // 99 * -19 = -1881
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("9");
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("99");

        presenter.onOperatorPressed('*');
        verify(mockView).setDisplayValue("99*");

        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("99*-");
        presenter.onDigitPressed(1);
        verify(mockView).setDisplayValue("99*-1");
        presenter.onDigitPressed(9);
        verify(mockView).setDisplayValue("99*-19");

        presenter.onEqualsButtonPressed();
        assertEquals(-1881, realModel.getResult());
        verify(mockView).setDisplayValue("-1881");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldCalculateDivision() {
        // 20 / 2 = 10
        presenter.onDigitPressed(2);
        verify(mockView).setDisplayValue("2");
        presenter.onDigitPressed(0);
        verify(mockView).setDisplayValue("20");

        presenter.onOperatorPressed('/');
        verify(mockView).setDisplayValue("20/");

        presenter.onDigitPressed(2);
        verify(mockView).setDisplayValue("20/2");

        presenter.onEqualsButtonPressed();
        assertEquals(10, realModel.getResult());
        verify(mockView).setDisplayValue("10");

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void shouldChainOperations() {
        // 20 / 2 = 10 + 5 = 15 * 2 = 30 - 1 = 29
        presenter.onDigitPressed(2);
        verify(mockView).setDisplayValue("2");
        presenter.onDigitPressed(0);
        verify(mockView).setDisplayValue("20");

        presenter.onOperatorPressed('/');
        verify(mockView).setDisplayValue("20/");

        presenter.onDigitPressed(2);
        verify(mockView).setDisplayValue("20/2");

        presenter.onEqualsButtonPressed();
        assertEquals(10, realModel.getResult());
        verify(mockView).setDisplayValue("10");

        presenter.onOperatorPressed('+');
        verify(mockView).setDisplayValue("10+");

        presenter.onDigitPressed(5);
        verify(mockView).setDisplayValue("10+5");

        presenter.onEqualsButtonPressed();
        assertEquals(15, realModel.getResult());
        verify(mockView).setDisplayValue("15");

        presenter.onOperatorPressed('*');
        verify(mockView).setDisplayValue("15*");

        presenter.onDigitPressed(2);
        verify(mockView).setDisplayValue("15*2");

        presenter.onEqualsButtonPressed();
        assertEquals(30, realModel.getResult());
        verify(mockView).setDisplayValue("30");

        presenter.onOperatorPressed('-');
        verify(mockView).setDisplayValue("30-");

        presenter.onDigitPressed(1);
        verify(mockView).setDisplayValue("30-1");

        presenter.onEqualsButtonPressed();
        assertEquals(29, realModel.getResult());
        verify(mockView).setDisplayValue("29");

        verifyNoMoreInteractions(mockView);
    }
}