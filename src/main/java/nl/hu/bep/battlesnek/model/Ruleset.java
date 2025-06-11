package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ruleset {

    private String rulesetName;
    private String version;

    public String getRulesetName() { return rulesetName; }
    public void setRulesetName(String rulesetName) { this.rulesetName = rulesetName; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
