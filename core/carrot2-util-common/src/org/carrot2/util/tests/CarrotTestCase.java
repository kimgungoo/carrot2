
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2012, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.util.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fest.assertions.AssertExtension;
import org.fest.assertions.Assertions;
import org.fest.assertions.BigDecimalAssert;
import org.fest.assertions.BooleanArrayAssert;
import org.fest.assertions.BooleanAssert;
import org.fest.assertions.ByteArrayAssert;
import org.fest.assertions.ByteAssert;
import org.fest.assertions.CharArrayAssert;
import org.fest.assertions.CharAssert;
import org.fest.assertions.CollectionAssert;
import org.fest.assertions.DoubleArrayAssert;
import org.fest.assertions.DoubleAssert;
import org.fest.assertions.FileAssert;
import org.fest.assertions.FloatArrayAssert;
import org.fest.assertions.FloatAssert;
import org.fest.assertions.ImageAssert;
import org.fest.assertions.IntArrayAssert;
import org.fest.assertions.IntAssert;
import org.fest.assertions.IteratorAssert;
import org.fest.assertions.ListAssert;
import org.fest.assertions.LongArrayAssert;
import org.fest.assertions.LongAssert;
import org.fest.assertions.MapAssert;
import org.fest.assertions.ObjectArrayAssert;
import org.fest.assertions.ObjectAssert;
import org.fest.assertions.ShortArrayAssert;
import org.fest.assertions.ShortAssert;
import org.fest.assertions.StringAssert;
import org.fest.assertions.ThrowableAssert;

import com.carrotsearch.randomizedtesting.RandomizedTest;
import com.carrotsearch.randomizedtesting.annotations.Timeout;

/**
 * Base class for Carrot2 test classes. Contains common hooks and setups.
 */
@Timeout(millis = 60 * 1000) // No test should last longer than 60 seconds.
public class CarrotTestCase extends RandomizedTest
{
    /*
     * Declare fest-assertion shortcuts. If not declared there is no way to use static
     * imports because superclass's Assert.assertThat always takes precedence.   
     */

    public static BigDecimalAssert assertThat(BigDecimal actual) { return Assertions.assertThat(actual); }
    public static BooleanAssert    assertThat(boolean actual)    { return Assertions.assertThat(actual); }
    public static BooleanAssert assertThat(Boolean actual) { return Assertions.assertThat(actual); }
    public static BooleanArrayAssert assertThat(boolean[] actual) { return Assertions.assertThat(actual); }
    public static ImageAssert assertThat(BufferedImage actual) { return Assertions.assertThat(actual); }
    public static ByteAssert assertThat(byte actual) { return Assertions.assertThat(actual); }
    public static ByteAssert assertThat(Byte actual) { return Assertions.assertThat(actual); }
    public static ByteArrayAssert assertThat(byte[] actual) { return Assertions.assertThat(actual); }
    public static CharAssert assertThat(char actual) { return Assertions.assertThat(actual); }
    public static CharAssert assertThat(Character actual) { return Assertions.assertThat(actual); }
    public static CharArrayAssert assertThat(char[] actual) { return Assertions.assertThat(actual); }
    public static CollectionAssert assertThat(Collection<?> actual) { return Assertions.assertThat(actual); }
    public static ListAssert assertThat(List<?> actual) { return Assertions.assertThat(actual); }
    public static DoubleAssert assertThat(double actual) { return Assertions.assertThat(actual); }
    public static DoubleAssert assertThat(Double actual) { return Assertions.assertThat(actual); }
    public static DoubleArrayAssert assertThat(double[] actual) { return Assertions.assertThat(actual); }
    public static FileAssert assertThat(File actual) { return Assertions.assertThat(actual); }
    public static FloatAssert assertThat(float actual) { return Assertions.assertThat(actual); }
    public static FloatAssert assertThat(Float actual) { return Assertions.assertThat(actual); }
    public static FloatArrayAssert assertThat(float[] actual) { return Assertions.assertThat(actual); }
    public static IntAssert assertThat(int actual) { return Assertions.assertThat(actual); }
    public static IntAssert assertThat(Integer actual) { return Assertions.assertThat(actual); }
    public static IntArrayAssert assertThat(int[] actual) { return Assertions.assertThat(actual); }
    public static IteratorAssert assertThat(Iterable<?> actual) { return Assertions.assertThat(actual); }
    public static IteratorAssert assertThat(Iterator<?> actual) { return Assertions.assertThat(actual); }
    public static LongAssert assertThat(long actual) { return Assertions.assertThat(actual); }
    public static LongAssert assertThat(Long actual) { return Assertions.assertThat(actual); }
    public static LongArrayAssert assertThat(long[] actual) { return Assertions.assertThat(actual); }
    public static MapAssert assertThat(Map<?, ?> actual) { return Assertions.assertThat(actual); }
    public static ObjectAssert assertThat(Object actual) { return Assertions.assertThat(actual); }
    public static ObjectArrayAssert assertThat(Object[] actual) { return Assertions.assertThat(actual); }
    public static ShortAssert assertThat(short actual) { return Assertions.assertThat(actual); }
    public static ShortAssert assertThat(Short actual) { return Assertions.assertThat(actual); }
    public static ShortArrayAssert assertThat(short[] actual) { return Assertions.assertThat(actual); }
    public static StringAssert assertThat(String actual) { return Assertions.assertThat(actual); }
    public static <T extends AssertExtension> T assertThat(T assertion) { return Assertions.assertThat(assertion); }
    public static ThrowableAssert assertThat(Throwable actual) { return Assertions.assertThat(actual); }
}
