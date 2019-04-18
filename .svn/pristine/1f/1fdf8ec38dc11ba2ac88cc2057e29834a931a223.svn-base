package com.yixin.alixjob.utils.javaShellUtils;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

@Slf4j
public class JavaShellUtil {

    public static int ExecCommand(String command) {
        int retCode = 0;
        try {
            Process process = Runtime.getRuntime().exec("sh "+command);
            ExecOutput(process);
            retCode = process.waitFor();
        } catch (Exception e) {
            retCode = -1;
        }
        return retCode;
    }


    public static boolean ExecOutput(Process process) throws Exception {
        if (process == null) {
            return false;
        } else {
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            InputStreamReader ier = new InputStreamReader(process.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            LineNumberReader ierinput = new LineNumberReader(ier);
            String line;
            while ((line = input.readLine()) != null) {
//                log.info(line);
            }

            while ((line = ierinput.readLine()) != null) {
                log.error(line);

            }

            input.close();
            ir.close();
            ier.close();
            ierinput.close();

        }
        return true;
    }
}
