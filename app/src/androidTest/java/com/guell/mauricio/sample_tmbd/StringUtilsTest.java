package com.guell.Mauricio.sample_tmbd;

import android.support.test.runner.AndroidJUnit4;

import app.awok.utils.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Test for StringUtils.
 *
 * Created by shweta 23/05/2017.
 */

@RunWith(AndroidJUnit4.class)
public class StringUtilsTest {

    /**
     * Ensures that method processHtmlString works correctly if you pass a bold html text.
     * The method must return a normal String.
     */
    @Test
    public void processBoldHtmlText() throws Exception {
        final String htmlBoldText = "<b>Bold text.</b>";
        final String expectedProcessingResult = "Bold text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlBoldText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass an italic html text.
     * The method must return a normal String.
     */
    @Test
    public void processItalicHtmlText() throws Exception {
        final String htmlItalicText = "<i>Italic text.</i>";
        final String expectedProcessingResult = "Italic text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlItalicText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a citation html text.
     * The method must return a normal String.
     */
    @Test
    public void processCitationHtmlText() throws Exception {
        final String htmlCitationText = "<cite>Citation text.</cite>";
        final String expectedProcessingResult = "Citation text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlCitationText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a emphasized html text.
     * The method must return a normal String.
     */
    @Test
    public void processEmphasizedHtmlText() throws Exception {
        final String htmlEmphasizedText = "<em>Emphasized text.</em>";
        final String expectedProcessingResult = "Emphasized text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlEmphasizedText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a code html text.
     * The method must return a normal String.
     */
    @Test
    public void processCodeHtmlText() throws Exception {
        final String htmlCodeText = "<code style=\"color:black\">Code text.</code>";
        final String expectedProcessingResult = "Code text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlCodeText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a big html text.
     * The method must return a normal String.
     *
     * @see [[StringUtils.processHtmlString()]]
     */
    @Test
    public void processBigHtmlText() throws Exception {
        final String htmlBigText = "<big>Big text.</big>";
        final String expectedProcessingResult = "Big text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlBigText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass an small html text.
     * The method must return a normal String.
     */
    @Test
    public void processSmallHtmlText() throws Exception {
        final String htmlSmallText = "<small>Small text.</small>";
        final String expectedProcessingResult = "Small text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlSmallText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a delete html text.
     * The method must return a normal String.
     */
    @Test
    public void processDeleteHtmlText() throws Exception {
        final String htmlDeleteText = "<del>Delete this text.</del>";
        final String expectedProcessingResult = "Delete this text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlDeleteText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass an insert html text.
     * The method must return a normal String.
     */
    @Test
    public void processInsertHtmlText() throws Exception {
        final String htmlInsertText = "<ins>Insert this text.</ins>";
        final String expectedProcessingResult = "Insert this text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlInsertText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a keyboard html text.
     * The method must return a normal String.
     */
    @Test
    public void processKeyboardHtmlText() throws Exception {
        final String htmlKeyboardText = "<kbd>Keyboard text - text to be entered by the user.</kbd>";
        final String expectedProcessingResult = "Keyboard text - text to be entered by the user.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlKeyboardText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a quotation html text.
     * The method must return a normal String.
     */
    @Test
    public void processQuotationHtmlText() throws Exception {
        final String htmlQuotationText = "<q>Quotation text.</q>";
        final String expectedProcessingResult = "Quotation text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlQuotationText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a sample html text.
     * The method must return a normal String.
     */
    @Test
    public void processSampleHtmlText() throws Exception {
        final String htmlSampleText = "<samp>Sample text (output from a computer program).</samp>";
        final String expectedProcessingResult = "Sample text (output from a computer program).";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlSampleText));
    }

    /**
     * Ensures that method processHtmlString works correctly if you pass a variable html text.
     * The method must return a normal String.
     */
    @Test
    public void processVariableHtmlText() throws Exception {
        final String htmlVariableText = "<var>Variable text.</var>";
        final String expectedProcessingResult = "Variable text.";
        assertEquals(expectedProcessingResult, StringUtils.processHtmlString(htmlVariableText));
    }
}
