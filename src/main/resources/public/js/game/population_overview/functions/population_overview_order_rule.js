const PopulationOverviewOrderRuleType = {
    NAME: "name",
    SKILL: "skill"
}

const PopulationOverviewOrderMethod = {
    ASC: "asc",
    DESC: "desc"
}

function PopulationOverviewOrderRule(){
    let ruleType = PopulationOverviewOrderRuleType.NAME;
    let byNameOrderMethod = PopulationOverviewOrderMethod.ASC;
    let bySkillOrderMethod = PopulationOverviewOrderMethod.ASC;
    let selectedSkillType = null;

    this.getComparator = function(){
        logService.logToConsole({
            ruleType: ruleType,
            byNameOrderMethod, byNameOrderMethod,
            bySkillOrderMethod: bySkillOrderMethod,
            selectedSkillType: selectedSkillType
        });
        return function(a, b){
            switch(ruleType){
                case PopulationOverviewOrderRuleType.NAME:
                    return (byNameOrderMethod == PopulationOverviewOrderMethod.DESC ? -1 : 1) * (a.name.localeCompare(b.name));
                break;
                case PopulationOverviewOrderRuleType.SKILL:
                    return (bySkillOrderMethod == PopulationOverviewOrderMethod.DESC ? -1 : 1) * (getSkillLevel(a, selectedSkillType) - getSkillLevel(b, selectedSkillType));
                break;
            }
        }
    }

    this.setRuleType = function(r){
        ruleType = r;
    }

    this.setByNameOrderMethod = function(b){
        byNameOrderMethod = b;
    }

    this.setBySkillOrderMethod = function(b){
        bySkillOrderMethod = b;
    }

    this.setSelectedSkillType = function(s){
        selectedSkillType = s;
    }

    function getSkillLevel(citizen, skillType){
        return new Stream(citizen.skills)
            .filter(function(skill){return skill.skillType == skillType})
            .map(function(skill){return skill.level})
            .findFirst();
    }
}