/*-
 * -\-\-
 * Spotify Styx Common
 * --
 * Copyright (C) 2016 - 2017 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.styx.util;

import com.spotify.styx.state.Trigger;
import com.spotify.styx.state.TriggerVisitor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility for getting information about {@link Trigger}s
 */
public class TriggerUtil {

  public static final String NATURAL_TRIGGER_ID = "natural-trigger";

  private TriggerUtil() {
  }

  public static boolean isBackfill(Trigger trigger) {
    return trigger.accept(TriggerIsBackfillVisitor.INSTANCE);
  }

  public static String name(Trigger trigger) {
    return trigger.accept(TriggerNameVisitor.INSTANCE);
  }

  public static String triggerId(Trigger trigger) {
    return trigger.accept(TriggerIdVisitor.INSTANCE);
  }

  public static List<String> triggerTypesList() {
    return Arrays.stream(TriggerVisitor.class.getDeclaredMethods())
        .map(Method::getName)
        .collect(Collectors.toList());
  }

  private enum TriggerIsBackfillVisitor implements TriggerVisitor<Boolean> {
    INSTANCE;

    @Override
    public Boolean natural() {
      return false;
    }

    @Override
    public Boolean adhoc(String triggerId) {
      return false;
    }

    @Override
    public Boolean backfill(String triggerId) {
      return true;
    }

    @Override
    public Boolean unknown(String triggerId) {
      return false;
    }
  }

  /**
   * A {@link TriggerVisitor} for extracting the name of a {@link Trigger}.
   */
  private enum TriggerNameVisitor implements TriggerVisitor<String> {
    INSTANCE;

    @Override
    public String natural() {
      return "natural";
    }

    @Override
    public String adhoc(String triggerId) {
      return "adhoc";
    }

    @Override
    public String backfill(String triggerId) {
      return "backfill";
    }

    @Override
    public String unknown(String triggerId) {
      return "unknown";
    }
  }

  /**
   * A {@link TriggerVisitor} for extracting the id of a {@link Trigger}.
   */
  private enum TriggerIdVisitor implements TriggerVisitor<String> {
    INSTANCE;

    @Override
    public String natural() {
      return NATURAL_TRIGGER_ID;
    }

    @Override
    public String adhoc(String triggerId) {
      return triggerId;
    }

    @Override
    public String backfill(String triggerId) {
      return triggerId;
    }

    @Override
    public String unknown(String triggerId) {
      return triggerId;
    }
  }
}
