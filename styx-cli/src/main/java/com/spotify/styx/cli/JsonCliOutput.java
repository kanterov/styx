/*-
 * -\-\-
 * Spotify Styx CLI
 * --
 * Copyright (C) 2016 Spotify AB
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

package com.spotify.styx.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Throwables;
import com.spotify.styx.api.BackfillPayload;
import com.spotify.styx.api.RunStateDataPayload;
import com.spotify.styx.model.Backfill;
import com.spotify.styx.model.Resource;
import com.spotify.styx.serialization.Json;
import java.util.List;

/**
 * Cli output printer that prints json output
 */
class JsonCliOutput implements CliOutput {

  private static void printJson(Object object) {
    try {
      System.out.println(Json.serialize(object).utf8());
    } catch (JsonProcessingException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void printStates(RunStateDataPayload runStateDataPayload) {
    printJson(CliUtil.groupStates(runStateDataPayload.activeStates()));
  }

  @Override
  public void printEvents(List<EventInfo> eventInfos) {
    printJson(eventInfos);
  }

  @Override
  public void printBackfill(Backfill backfill) {
    printJson(backfill);
  }

  @Override
  public void printBackfillPayload(BackfillPayload backfillPayload) {
    printJson(backfillPayload);
  }

  @Override
  public void printBackfills(List<BackfillPayload> backfills) {
    printJson(backfills);
  }

  @Override
  public void printResources(List<Resource> resources) {
    printJson(resources);
  }
}
