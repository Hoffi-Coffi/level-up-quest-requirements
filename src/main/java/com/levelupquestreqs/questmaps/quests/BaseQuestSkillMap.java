/*
 * Copyright (c) 2023, Hoffi Coffi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.levelupquestreqs.questmaps.quests;

import lombok.Getter;
import net.runelite.api.Quest;
import net.runelite.api.Skill;

import java.util.EnumMap;
import java.util.Map;

public abstract class BaseQuestSkillMap
{
    @Getter
    protected Quest quest;

    protected Map<Skill, Integer> skillReqs = new EnumMap<>(Skill.class);

    public boolean newSkillLevelMeetsRequirements(Map<Skill, Integer> previousLevels, Skill skillLevelled, Integer newLevel)
    {
        // Return early if the skill levelled doesn't get us to the requirements.
        if (newLevel != skillReqs.getOrDefault(skillLevelled, 99)) return false;

        final Map<Skill, Integer> otherSkillReqs = skillReqs;
        otherSkillReqs.remove(skillLevelled);

        if (!otherSkillReqs.isEmpty())
        {
            // If any of the other skills are below the requirement, return early.
            for (Map.Entry<Skill, Integer> reqEntry : otherSkillReqs.entrySet())
            {
                if (previousLevels.getOrDefault(reqEntry.getKey(), -1) < reqEntry.getValue()) return false;
            }
        }

        return true;
    }

    public boolean hasSkillAsRequirement(Skill skill)
    {
        return skillReqs.containsKey(skill);
    }
}
