package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exports Life to CSV file.
 */
public class ExportFile {
    /**
     * Reads Life and exports to CSV file to be imported on another Life application.
     *
     * @param fileToExport path of Life to be exported.
     * @param fileToSave   name of file to be saved.
     * @throws CommandException if file name already exist or path provided instead of file.
     */
    public void exportCsv(String fileToExport, String fileToSave) throws CommandException {
        try {
            JsonNode jsonFile = new ObjectMapper().readTree(new File(fileToExport));

            JsonNode jsonTree = jsonFile.get("persons");
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(fieldName -> {
                csvSchemaBuilder.addColumn(fieldName);
            });
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File(fileToSave), jsonTree);
        } catch (Exception ioe) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
    }

    /**
     * Reads Life groups and exports to CSV file to be imported on another Life application.
     *
     * @param fileToExport path of Life groups to be exported.
     * @param fileToSave   name of file to be saved.
     * @throws CommandException if file name already exist or path provided instead of file.
     */
    public void exportGroupCsv(String fileToExport, String fileToSave) throws CommandException {
        try {
            JsonNode jsonFile = new ObjectMapper().readTree(new File(fileToExport));

            JsonNode jsonTree = jsonFile.get("groups");
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(fieldName -> {
                csvSchemaBuilder.addColumn(fieldName);
            });
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File(fileToSave), jsonTree);
        } catch (IOException ioe) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
    }

    /**
     * Reads Life events and exports to CSV file to be imported on another Life application.
     *
     * @param fileToExport path of Life events to be exported.
     * @param fileToSave   name of file to be saved.
     * @throws CommandException if file name already exist or path provided instead of file.
     */
    public void exportEventCsv(String fileToExport, String fileToSave) throws CommandException {
        try {
            JsonNode jsonFile = new ObjectMapper().readTree(new File(fileToExport));

            JsonNode jsonTree = jsonFile.get("events");
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(fieldName -> {
                csvSchemaBuilder.addColumn(fieldName);
            });
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File(fileToSave), jsonTree);
        } catch (IOException ioe) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
    }
}
