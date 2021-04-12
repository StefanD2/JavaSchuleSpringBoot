package at.ac.htlstp.deimel.springbootdemoserver.maxima;

import at.ac.htlstp.deimel.springbootdemoserver.dto.maxima.MaximaDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MaximaProcess {

    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private Process process;

    private List<MaximaDTO> commands;

    public static final long maxCommandTime = 30 * 1000; // in ms


    public MaximaProcess() {
        createProcess();
        commands = new ArrayList<>();

        // dump start infos
        readUntil('i');
    }

    private void createProcess() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("C:\\maxima-5.42.1\\maxima.bat");
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        inputStreamReader = new InputStreamReader(new BufferedInputStream(process.getInputStream()));
        outputStreamWriter =
                new OutputStreamWriter(new BufferedOutputStream(process.getOutputStream()));
    }

    private String readUntil(char inOut) {
        try {
            long startTime = System.currentTimeMillis();
            String read = "";
            while (!read.matches("(.|\\r|\\n)*\\(%" + inOut + "\\d+\\)") && startTime + maxCommandTime >= System.currentTimeMillis()) {
                if (inputStreamReader.ready()) {
                    read += (char) inputStreamReader.read();
                }
            }
            if (startTime + maxCommandTime < System.currentTimeMillis())
                return "TIMEOUT, please remove command and restart maxima";
            return read;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private void dumpInput() {
        readUntil('o');
    }

    private String readOutput() {
        return readUntil('i').replaceAll("\\(%i\\d+\\)", "");
    }

    private String executeCommandS(String command) {
        try {
            outputStreamWriter.write(command);
            outputStreamWriter.flush();
            dumpInput();
            String out = readOutput();
            return out;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void executeCommand(String command) {
        if (command == null || command.equals("") || !(command.endsWith(";") || !command.endsWith("$"))) {
            return;
        }
        // TODO check brackets and stuff?
        String out = executeCommandS(command);
        commands.add(new MaximaDTO(commands.size(), command, out));
    }

    public List<MaximaDTO> getCommands() {
        return commands;
    }

    public void destroy() {
        try {
            outputStreamWriter.write("quit();");
            outputStreamWriter.flush();
        } catch (IOException e) {
        }
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }

    public void restartProcess() {
        process.destroyForcibly();
        createProcess();
        for (int i = 0; i < commands.size(); i++) {
            MaximaDTO maximaDTO = commands.get(i);
            maximaDTO.setOutput(""); // output is invalid because maxima is restarted
            commands.set(i, maximaDTO);
        }
    }

    public void rerunCommands() {
        for (int i = 0; i < commands.size(); i++) {
            // no syntax checks needed, already done before saving command
            MaximaDTO maximaDTO = commands.get(i);
            maximaDTO.setOutput(executeCommandS(maximaDTO.getInput()));
            commands.set(i, maximaDTO);
        }
    }

    public void deleteCommand(int index) {
        commands.remove(index);
    }

}
