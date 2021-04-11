package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

import java.time.LocalDate;

public class Country {

    public final boolean customName;
    public final boolean autoShipDesigns;
    public final boolean hasAdvisor;
    public final boolean initialized;
    public final int colorIndex;
    public final int capital;
    public final int speciesIndex;
    public final int builtSpecies;
    public final int nextTransportFleetNumber;
    public final int nextArmyNumber;
    public final int ruler;
    public final int federation;
    public final int associatedFederation;
    public final int startingSystem;
    public final double militaryPower;
    public final double economyPower;
    public final int victoryRank;
    public final double victoryScore;
    public final double techPower;
    public final double immigration;
    public final double emigration;
    public final int fleetSize;
    public final int empireSize;
    public final double newColonies;
    public final double sapient;
    public final String name;
    public final String adjective;
    public final String graphicalCulture;
    public final String cityGraphicalCulture;
    public final String room;
    public final String lastAllianceName;
    public final String advisorVoiceType;
    public final String personality;
    public final String rulerTitle;
    public final String nameList;
    public final String shipPrefix;
    public final String type;
    public final String customization;
    public final LocalDate lastDateWasHuman;
    public final LocalDate lastDateWarLost;
    public final LocalDate lastDateAtWar;
    public final LocalDate nextElection;
    public final LocalDate governmentDate;
    public final ImmutableIntList surveyed;
    public final ImmutableIntList visitedObjects;
    public final ImmutableIntList intelLevels;
    public final ImmutableIntList highestIntelLevels;
    public final ImmutableIntList sensorRangeFleets;
    public final ImmutableIntList ownedLeaders;
    public final ImmutableIntList ownedFleets;
    public final ImmutableIntList ownedMegastructures;
    public final ImmutableIntList ownedArmies;
    public final ImmutableIntList ownedPlanets;
    public final ImmutableIntList branchOfficePlanets;
    public final ImmutableIntList restrictedSystems;
    public final ImmutableIntList controlledPlanets;
    public final ImmutableIntList shipDesigns;
    public final ImmutableIntList usableBypasses;
    public final ImmutableIntList hyperlaneSystems;
    public final Sectors sectors;
    public final ImmutableList<String> policyFlags;
    public final ImmutableList<String> shownMessageTypes;
    public final ImmutableList<String> traditions;
    public final ImmutableList<String> ascensionPerks;
    public final ImmutableList<String> seenBypassTypes;
    public final ImmutableIntList seenBypasses;
    public final ImmutableList<Intel> intel;
    public final ImmutableList<TimedModifier> timedModifiers;
    public final ImmutableList<ActivePolicy> activePolicies;
    public final ImmutableList<Edict> edicts;
    public final ImmutableList<RegnalNumber> regnalNumbers;
    public final ImmutableList<Species> speciesModTemplates;
    public final Flag flag;
    public final TechStatus techStatus;
    public final CountryBudget budget;
    public final Events events;
    public final TerraIncognita terraIncognita;
    public final AI ai;
    public final Ethos ethos;
    public final FleetTemplateManager fleetTemplateManager;
    public final Government government;
    public final ImmutableMap<String, FlagData> flags;
    public final ImmutableObjectDoubleMap<String> variables;
    public final Faction faction;
    public final ImmutableMap<String, ImmutableIntList> shipNames;
    public final ImmutableIntObjectMap<ImmutableList<Property>> controlGroups;
    public final Modules modules;
    public final RandomNameVariables randomNameVariables;
    public final RelationsManager relationsManager;
    public final Property location;
    public final ImmutableIntList warAllies;
    public final LocalDate lastChangedCountryType;
    public final ImmutableList<String> relics;
    public final String lastActivatedRelic;
    public final String lastReceivedRelic;
    public final double givenValue;
    public final int numUpgradedStarbase;
    public final int starbaseCapacity;
    public final int edictCapacity;
    public final int employablePops;
    public final ImmutableIntList ownedSpecies;
    public final ImmutableIntList enslavedSpecies;

    public Country(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.flag = o.getObjectAs("flag", Flag::new);
        this.colorIndex = o.getInt("color_index");
        this.name = o.getString("name");
        this.adjective = o.getString("adjective");
        this.customName = o.getBoolean("custom_name", false);
        this.techStatus = o.getObjectAs("tech_status", TechStatus::new);
        this.autoShipDesigns = o.getBoolean("auto_ship_designs", true);
        this.lastDateWasHuman = o.getDate("last_date_was_human");
        this.lastDateWarLost = o.getDate("last_date_war_lost");
        this.lastDateAtWar = o.getDate("last_date_at_war");
        this.budget = o.getObjectAs("budget", CountryBudget::new);
        this.events = o.getObjectAs("events", Events::new);
        this.terraIncognita = o.getObjectAs("terra_incognita", TerraIncognita::new);
        this.militaryPower = o.getDouble("military_power");
        this.economyPower = o.getDouble("economy_power");
        this.victoryRank = o.getInt("victory_rank", -1);
        this.victoryScore = o.getDouble("victory_score");
        this.techPower = o.getDouble("tech_power");
        this.immigration = o.getDouble("immigration");
        this.emigration = o.getDouble("emigration");
        this.fleetSize = o.getInt("fleet_size");
        this.empireSize = o.getInt("empire_size", 0);
        this.newColonies = o.getDouble("new_colonies");
        this.sapient = o.getInt("sapient", 0);
        this.graphicalCulture = o.getString("graphical_culture", null);
        this.cityGraphicalCulture = o.getString("city_graphical_culture", null);
        this.room = o.getString("room", null);
        this.ai = o.getObjectAs("ai", AI::new);
        this.capital = o.getInt("capital", -1);
        this.speciesIndex = o.getInt("species_index");
        this.builtSpecies = o.getInt("built_species", -1);
        this.ethos = o.getObjectAsNullOr("ethos", Ethos::new);
        this.lastAllianceName = o.getString("last_alliance_name", null);
        this.fleetTemplateManager = o.getObjectAs("fleet_template_manager", FleetTemplateManager::new);
        this.government = o.getObjectAsNullOr("government", Government::new);
        this.advisorVoiceType = o.getString("advisor_voice_type", null);
        this.personality = o.getString("personality", null);
        this.rulerTitle = o.getString("ruler_title", null);
        this.nextElection = o.getDate("next_election");
        this.governmentDate = o.getDate("government_date");
        this.surveyed = o.getListAsEmptyOrIntList("surveyed");
        this.visitedObjects = o.getListAsIntList("visited_objects");
        this.intelLevels = o.getListAsIntList("intel_level");
        this.highestIntelLevels = o.getListAsEmptyOrIntList("highest_intel_level");
        this.intel = o.getListAsEmptyOrList("intel", Intel::new);
        this.timedModifiers = o.getImplicitListAsList("timed_modifier", TimedModifier::new);
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.variables = o.getObjectAsEmptyOrStringDoubleMap("variables");
        this.nextTransportFleetNumber = o.getInt("next_transport_fleet_number", -1);
        this.nextArmyNumber = o.getInt("next_army_number", -1);
        this.sensorRangeFleets = o.getListAsEmptyOrIntList("sensor_range_fleets");
        this.faction = o.getObjectAs("faction", Faction::new);
        this.nameList = o.getString("name_list");
        this.shipNames = o.getObjectAsEmptyOrStringObjectMap("ship_names", _s -> _s.expectImplicitList().getAsIntList());
        this.ruler = o.getInt("ruler", -1);
        this.controlGroups = o.getObjectAsEmptyOrIntObjectMap("control_groups", _s -> _s.expectList().getAsList(Property::new));
        this.shipPrefix = o.getString("ship_prefix", null);
        this.activePolicies = o.getListAsList("active_policies", ActivePolicy::new);
        this.policyFlags = o.getListAsStringList("policy_flags");
        this.federation = o.getInt("federation", -1);
        this.associatedFederation = o.getInt("associated_federation", -1);
        // TODO: subjects
        this.startingSystem = o.getInt("starting_system", -1);
        this.hasAdvisor = o.getBoolean("has_advisor", true);
        this.shownMessageTypes = o.getListAsEmptyOrStringList("shown_message_types");
        this.ownedLeaders = o.getListAsIntList("owned_leaders");
        this.ownedFleets = o.getListAsEmptyOrIntList("owned_fleets");
        this.ownedMegastructures = o.getListAsEmptyOrIntList("owned_megastructures");
        this.traditions = o.getListAsEmptyOrStringList("traditions");
        this.ascensionPerks = o.getListAsEmptyOrStringList("ascension_perks");
        this.ownedArmies = o.getListAsEmptyOrIntList("owned_armies");
        this.ownedPlanets = o.getListAsEmptyOrIntList("owned_planets");
        this.branchOfficePlanets = o.getListAsEmptyOrIntList("branch_office_planets");
        this.restrictedSystems = o.getListAsEmptyOrIntList("restricted_systems");
        this.controlledPlanets = o.getListAsEmptyOrIntList("controlled_planets");
        this.shipDesigns = o.getListAsEmptyOrIntList("ship_design");
        this.edicts = o.getListAsEmptyOrList("edicts", Edict::new);
        this.type = o.getString("type");
        this.modules = o.getObjectAs("modules", Modules::new); // TODO: check
        this.initialized = o.getBoolean("initialized");
        this.regnalNumbers = o.getListAsEmptyOrList("regnal_numbers", RegnalNumber::new);
        this.randomNameVariables = o.getObjectAs("random_name_variables", RandomNameVariables::new);
        this.relationsManager = o.getObjectAs("relations_manager", RelationsManager::new); // TODO: check
        this.location = o.getObjectAsNullOr("location", Property::new);
        this.warAllies = o.getListAsEmptyOrIntList("war_allies");
        this.speciesModTemplates = o.getListAsEmptyOrList("species_mod_templates", Species::new);
        this.customization = o.getString("customization");
        this.lastChangedCountryType = o.getDate("last_changed_country_type", null);
        this.seenBypassTypes = o.getListAsEmptyOrStringList("seen_bypass_types");
        this.seenBypasses = o.getListAsEmptyOrIntList("seen_bypasses");
        this.relics = o.getListAsEmptyOrStringList("relics");
        this.lastActivatedRelic = o.getString("last_activated_relic", null);
        this.lastReceivedRelic = o.getString("last_received_relic", null);
        this.usableBypasses = o.getListAsEmptyOrIntList("usable_bypasses");
        this.hyperlaneSystems = o.getListAsEmptyOrIntList("hyperlane_systems");
        this.sectors = o.getObjectAs("sectors", Sectors::new);
        this.givenValue = o.getDouble("given_value");
        this.numUpgradedStarbase = o.getInt("num_upgraded_starbase");
        this.starbaseCapacity = o.getInt("starbase_capacity");
        this.edictCapacity = o.getInt("edict_capacity");
        this.employablePops = o.getInt("employable_pops", 0);
        this.ownedSpecies = o.getListAsEmptyOrIntList("owned_species");
        this.enslavedSpecies = o.getListAsEmptyOrIntList("enslaved_species");
    }
}
