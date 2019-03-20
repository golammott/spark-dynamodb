package org.apache.spark.datasources.dynamodb.reader

import org.apache.spark.sql.sources.v2.reader.{DataSourceReader, InputPartition};
import software.amazon.awssdk.services.dynamodb.model.{DescribeTableRequest, DescribeTableResponse}
import org.apache.spark.sql.sources.v2.DataSourceOptions
import org.apache.spark.datasources.dynamodb.{DynamodbDataSourceOptions, DynamoClient}
import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.types.StructType;

class DynamodbDataSourceReader(options: DynamodbDataSourceOptions) extends DataSourceReader {
  
  def readSchema(): StructType = {
    // figure out how to get the user defined schema instead of defaulting to only keys for the table
    val ddbClient = new DynamoClient("us-west-2")
    ddbClient.getTable().table().attributeDefinitions()
    
    //StructType()
  }
  //List<InputPartition<InternalRow>> planInputPartitions();
  def planInputPartitions(): Seq[InputPartition[InternalRow]] = {
    val totalSegments = options.getSegments()
    (0 until totalSegments).map(segmentNumber => new DynamodbInputPartition(segmentNumber, totalSegments))
  }
}