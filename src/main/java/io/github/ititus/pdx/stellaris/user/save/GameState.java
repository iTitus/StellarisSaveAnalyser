package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

import java.time.LocalDate;

import static io.github.ititus.pdx.pdxscript.PdxHelper.nullOr;

public class GameState {

    public final String version;
    public final int versionControlRevision;
    public final String name;
    public final LocalDate date;
    public final ImmutableList<String> requiredDLCs;
    public final ImmutableList<Player> players;
    public final int tick;
    public final int randomLogDay;
    public final ImmutableList<Species> species;
    public final int lastCreatedSpecies;
    public final ImmutableList<Nebula> nebulas;
    public final ImmutableIntObjectMap<Pop> pops;
    public final int lastCreatedPop;
    public final ImmutableIntObjectMap<GalacticObject> galacticObjects;
    public final StarbaseManager starbaseManager;
    public final Planets planets;
    public final ImmutableIntObjectMap<Country> countries;
    public final ImmutableIntObjectMap<Federation> federations;
    public final ImmutableIntObjectMap<Truce> truces;
    public final ImmutableIntObjectMap<TradeDeal> tradeDeals;
    public final int lastCreatedCountry;
    public final int lastRefugeeCountry;
    public final int lastCreatedSystem;
    public final ImmutableIntObjectMap<Leader> leaders;
    public final ImmutableObjectIntMap<String> savedLeaders;
    public final ImmutableLongObjectMap<Ship> ships;
    public final ImmutableIntObjectMap<Fleet> fleets;
    public final ImmutableIntObjectMap<FleetTemplate> fleetTemplates;
    public final int lastCreatedFleet;
    public final int lastCreatedShip;
    public final int lastCreatedLeader;
    public final int lastCreatedArmy;
    public final int lastCreatedDesign;
    public final ImmutableIntObjectMap<Army> armies;
    public final ImmutableIntObjectMap<Deposit> deposits;
    public final ImmutableIntObjectMap<GroundCombat> groundCombats;
    public final ImmutableList<String> firedEventIds;
    public final ImmutableIntObjectMap<War> wars;
    public final ImmutableIntObjectMap<Debris> debris;
    public final ImmutableLongObjectMap<Missile> missiles;
    public final ImmutableIntObjectMap<StrikeCraft> strikeCrafts;
    public final ImmutableIntObjectMap<AmbientObject> ambientObjects;
    public final int lastCreatedAmbientObject;
    public final ImmutableList<Message> messages;
    public final int lastDiploAction;
    public final int lastNotificationId;
    public final int lastEventId;
    public final RandomNameDatabase randomNameDatabase;
    public final NameList nameList;
    public final Galaxy galaxy;
    public final double galaxyRadius;
    public final ImmutableMap<String, FlagData> flags;
    public final ImmutableList<SavedEventTarget> savedEventTargets;
    public final ImmutableIntObjectMap<ShipDesign> shipDesigns;
    public final ImmutableIntObjectMap<PopFaction> popFactions;
    public final int lastCreatedPopFaction;
    public final String lastKilledCountryName;
    public final ImmutableIntObjectMap<MegaStructure> megaStructures;
    public final ImmutableIntObjectMap<Bypass> bypasses;
    public final ImmutableIntObjectMap<NaturalWormhole> naturalWormholes;
    public final ImmutableIntObjectMap<TradeRoute> tradeRoutes;
    public final ImmutableLongObjectMap<Sector> sectors;
    public final ImmutableIntObjectMap<Building> buildings;
    public final ArchaeologicalSites archaeologicalSites;
    public final ImmutableList<GlobalShipDesign> globalShipDesigns;
    public final ImmutableList<Cluster> clusters;
    public final ImmutableIntList rimGalacticObjects;
    public final boolean endGameCrisis;
    public final ImmutableList<String> usedColors;
    public final ImmutableLongList usedSymbols;
    public final ImmutableList<UsedSpeciesClassAssets> usedSpeciesNames;
    public final ImmutableList<UsedSpeciesClassAssets> usedSpeciesPortraits;
    public final int randomCount;
    public final int randomSeed;

    public GameState(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.version = o.getString("version");
        this.versionControlRevision = o.getInt("version_control_revision");
        this.name = o.getString("name");
        this.date = o.getDate("date");
        this.requiredDLCs = o.getListAsStringList("required_dlcs");
        this.players = o.getListAsList("player", Player::new);
        this.tick = o.getInt("tick");
        this.randomLogDay = o.getInt("random_log_day");
        this.species = o.getListAsList("species", Species::new);
        this.lastCreatedSpecies = o.getInt("last_created_species", -1);
        this.nebulas = o.getImplicitListAsList("nebula", Nebula::new);
        this.pops = o.getObjectAsIntObjectMap("pop", Pop::new);
        this.lastCreatedPop = o.getInt("last_created_pop", -1);
        this.galacticObjects = o.getObjectAsIntObjectMap("galactic_object", GalacticObject::new);
        this.starbaseManager = o.getObjectAs("starbase_mgr", StarbaseManager::new);
        this.planets = o.getObjectAs("planets", Planets::new);
        this.countries = o.getObjectAsIntObjectMap("country", nullOr(Country::new));
        // TODO: construction | BuildQueueItem got moved to construction.queue_mgr/item_mgr
        this.federations = o.getObjectAsIntObjectMap("federation", Federation::new);
        this.truces = o.getObjectAsIntObjectMap("truce", Truce::new);
        // TODO: trade_deal values might always be none?
        this.tradeDeals = o.getObjectAsIntObjectMap("trade_deal", nullOr(TradeDeal::new));
        this.lastCreatedCountry = o.getInt("last_created_country", -1);
        this.lastRefugeeCountry = o.getUnsignedInt("last_refugee_country");
        this.lastCreatedSystem = o.getInt("last_created_system", -1);
        this.leaders = o.getObjectAsIntObjectMap("leaders", nullOr(Leader::new));
        this.savedLeaders = o.getObjectAsStringUnsignedIntMap("saved_leaders");
        this.ships = o.getObjectAsLongObjectMap("ships", Ship::new);
        this.fleets = o.getObjectAsIntObjectMap("fleet", Fleet::new);
        this.fleetTemplates = o.getObjectAsIntObjectMap("fleet_template", FleetTemplate::new);
        this.lastCreatedFleet = o.getInt("last_created_fleet", -1);
        this.lastCreatedShip = o.getInt("last_created_ship", -1);
        this.lastCreatedLeader = o.getInt("last_created_leader", -1);
        this.lastCreatedArmy = o.getInt("last_created_army", -1);
        this.lastCreatedDesign = o.getInt("last_created_design", -1);
        this.armies = o.getObjectAsIntObjectMap("army", Army::new);
        this.deposits = o.getObjectAsIntObjectMap("deposit", nullOr(Deposit::new));
        this.groundCombats = o.getObjectAsIntObjectMap("ground_combat", GroundCombat::new);
        this.firedEventIds = o.getListAsStringList("fired_event_ids");
        this.wars = o.getObjectAsIntObjectMap("war", War::new);
        this.debris = o.getObjectAsIntObjectMap("debris", nullOr(Debris::new));
        this.missiles = o.getObjectAsLongObjectMap("missile", nullOr(Missile::new));
        this.strikeCrafts = o.getObjectAsIntObjectMap("strike_craft", nullOr(StrikeCraft::new));
        this.ambientObjects = o.getObjectAsIntObjectMap("ambient_object", nullOr(AmbientObject::new));
        this.lastCreatedAmbientObject = o.getInt("last_created_ambient_object", -1);
        this.messages = o.getImplicitListAsList("message", Message::new);
        this.lastDiploAction = o.getInt("last_diplo_action_id", -1);
        this.lastNotificationId = o.getInt("last_notification_id", -1);
        this.lastEventId = o.getInt("last_event_id", -1);
        this.randomNameDatabase = o.getObjectAs("random_name_database", RandomNameDatabase::new);
        this.nameList = o.getObjectAs("name_list", NameList::of);
        this.galaxy = o.getObjectAs("galaxy", Galaxy::new);
        this.galaxyRadius = o.getDouble("galaxy_radius");
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.savedEventTargets = o.getImplicitListAsList("saved_event_target", SavedEventTarget::new);
        this.shipDesigns = o.getObjectAsIntObjectMap("ship_design", nullOr(ShipDesign::new));
        this.popFactions = o.getObjectAsIntObjectMap("pop_factions", PopFaction::new);
        this.lastCreatedPopFaction = o.getInt("last_created_pop_faction", -1);
        this.lastKilledCountryName = o.getString("last_killed_country_name");
        this.megaStructures = o.getObjectAsIntObjectMap("megastructures", nullOr(MegaStructure::new));
        this.bypasses = o.getObjectAsIntObjectMap("bypasses", nullOr(Bypass::new));
        this.naturalWormholes = o.getObjectAsIntObjectMap("natural_wormholes", NaturalWormhole::new);
        this.tradeRoutes = o.getObjectAsIntObjectMap("trade_routes", TradeRoute::new);
        this.sectors = o.getObjectAsLongObjectMap("sectors", Sector::new);
        this.buildings = o.getObjectAsIntObjectMap("buildings", nullOr(Building::new));
        // TODO: resolution
        this.archaeologicalSites = o.getObjectAs("archaeological_sites", ArchaeologicalSites::new);
        this.globalShipDesigns = o.getListAsList("global_ship_design", GlobalShipDesign::new);
        this.clusters = o.getListAsList("clusters", Cluster::new);
        this.rimGalacticObjects = o.getListAsIntList("rim_galactic_objects");
        this.endGameCrisis = o.getBoolean("end_game_crisis", false);
        this.usedColors = o.getImplicitListAsStringList("used_color");
        this.usedSymbols = o.getListAsLongList("used_symbols");
        this.usedSpeciesNames = o.getImplicitListAsList("used_species_names", UsedSpeciesClassAssets::new);
        this.usedSpeciesPortraits = o.getImplicitListAsList("used_species_portrait", UsedSpeciesClassAssets::new);
        this.randomSeed = o.getUnsignedInt("random_seed");
        this.randomCount = o.getInt("random_count");
        // TODO: market
        // TODO: trade_routes_manager
        // TODO: slave_market_manager
        // TODO: galactic_community
    }
}
