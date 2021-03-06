package edu.ucla.nesl.sensorsafe.db;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ucla.nesl.sensorsafe.model.Macro;
import edu.ucla.nesl.sensorsafe.model.Rule;
import edu.ucla.nesl.sensorsafe.model.Stream;
import edu.ucla.nesl.sensorsafe.model.TemplateParameterDefinition;

public interface StreamDatabaseDriver extends DatabaseDriver {
	
	public List<Rule> getRules(String owner) throws SQLException;

	public List<Rule> getRulesWithTags(String owner, String tags) throws SQLException;

	public void addUpdateRuleTemplate(String owner, Rule rule) throws SQLException;
	
	public void deleteAllRules(String owner) throws SQLException;
	
	public void createStream(Stream stream) throws SQLException, ClassNotFoundException;
	
	public void addTuple(String owner, String streamName, String strTuple) throws SQLException;
	
	public boolean prepareQuery(String requestingUser,	 String streamOwner
			, String streamName, String startTime, String endTime
			, String aggregator, String filter
			, int limit, int offset, int skipEveryNth
			, boolean isUpdateNumSamples
			, String streamForRules) 
					throws SQLException, JsonProcessingException, ClassNotFoundException;
	
	public Stream getStream(String owner, String name) 	throws SQLException;
	
	public List<Stream> getStreamList(String owner) throws SQLException;

	public void deleteStream(String owner, String streamName, String startTime, String endTime) throws SQLException;
	
	public void deleteAllStreams(String owner) throws SQLException;

	public void clean() throws SQLException, ClassNotFoundException;

	public void deleteRule(String remoteUser, int id) throws SQLException;

	public void bulkLoad(String owner, String streamName, String data) throws SQLException, IOException, NoSuchAlgorithmException;

	public void addOrUpdateMacro(String owner, Macro macro) throws SQLException;

	public List<Macro> getMacros(String owner) throws SQLException;

	public void deleteAllMacros(String owner) throws SQLException;

	public void deleteMacro(String owner, int id, String name) throws SQLException;

	public void deleteTemplate(String ownerName, int id, String templateName) throws SQLException;

	public void deleteAllTemplates(String ownerName) throws SQLException;

	public List<Rule> getTemplates(String ownerName) throws SQLException;

	public void createRuleFromTemplate(String ownerName, TemplateParameterDefinition params) throws SQLException;

	public Stream getStoredStreamInfo();

	public Object[] getNextTuple() throws SQLException;

	public ResultSet getStoredResultSet();

	public boolean getNextTuple(Object[] tuple) throws SQLException;

}
