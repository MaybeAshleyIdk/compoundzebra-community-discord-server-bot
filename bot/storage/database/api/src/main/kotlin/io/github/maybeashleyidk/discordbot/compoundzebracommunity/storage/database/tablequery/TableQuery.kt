package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.tablequery

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

/**
 * Returns a [Flow] that — once a terminal operator is called on it —
 * executes the SQL statement `SELECT * FROM [tableName]`, calls [entityExtractor] for each row and
 * [emits][FlowCollector.emit] its return value.
 */
public suspend fun <EntityT : Any> Database.queryTable(
	tableName: String,
	entityExtractor: (row: RowResult) -> EntityT,
): Flow<EntityT> {
	require(tableName.isNotEmpty()) {
		"The table name must be not empty"
	}
	require(tableName.first().isLetter()) {
		"The table name must start with a letter"
	}
	require(tableName.all(Char::isLetterOrDigit)) {
		"The table name must only consist of letters and digits"
	}

	return flow {
		this@queryTable.visitConnectionForReading { connection: Connection ->
			connection.createStatement().use { statement: Statement ->
				queryTableFromStatement(statement, tableName, entityExtractor, flowCollector = this@flow)
			}
		}
	}
}

private suspend fun <EntityT : Any> queryTableFromStatement(
	statement: Statement,
	tableName: String,
	entityExtractor: (row: RowResult) -> EntityT,
	flowCollector: FlowCollector<EntityT>,
) {
	statement.executeQuery("SELECT * FROM `$tableName`").use { resultSet: ResultSet ->
		extractEntitiesFromResultSet(resultSet, entityExtractor, flowCollector)
	}
}

private suspend fun <EntityT : Any> extractEntitiesFromResultSet(
	resultSet: ResultSet,
	entityExtractor: (row: RowResult) -> EntityT,
	flowCollector: FlowCollector<EntityT>,
) {
	while (resultSet.next()) {
		val row: RowResult = ResultSetRow(resultSet)
		val entity: EntityT = entityExtractor(row)
		flowCollector.emit(entity)
	}
}
