/*-
 * -\-\-
 * Spotify Styx Common
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

package com.spotify.styx.model.deprecated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import java.util.Comparator;

/**
 * An instantiation of a {@link Workflow}.
 */
@AutoValue
@Deprecated
public abstract class WorkflowInstance {

  public static final Comparator<WorkflowInstance> KEY_COMPARATOR =
      (a, b) -> a.toKey().compareTo(b.toKey());

  @JsonProperty
  public abstract WorkflowId workflowId();

  /**
   * This property contains the thing needed to turn a general purpose Workflow into something
   * that satisfies a specific data endpoint partition. Might be for example
   * '-Pdatehour=2016-01-01T08'.
   */
  @JsonProperty
  public abstract String parameter();

  public String toKey() {
    return workflowId().toKey() + "#" + parameter();
  }

  @JsonCreator
  public static WorkflowInstance create(
      @JsonProperty("workflow_id") WorkflowId workflowId,
      @JsonProperty("parameter") String parameter) {
    return new AutoValue_WorkflowInstance(workflowId, parameter);
  }

  public static WorkflowInstance create(com.spotify.styx.model.WorkflowInstance workflowInstance) {
    return create(WorkflowId.create(workflowInstance.workflowId()),
                  workflowInstance.parameter());
  }

  public static com.spotify.styx.model.WorkflowInstance create(WorkflowInstance workflowInstance) {
    return com.spotify.styx.model.WorkflowInstance.create(
        com.spotify.styx.model.WorkflowId.create(workflowInstance.workflowId().componentId(),
                                                 workflowInstance.workflowId().endpointId()),
        workflowInstance.parameter());
  }
}
