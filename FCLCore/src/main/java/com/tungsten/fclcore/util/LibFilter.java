package com.tungsten.fclcore.util;

import static com.tungsten.fclcore.util.gson.JsonUtils.GSON;

import com.tungsten.fclcore.game.Library;
import com.tungsten.fclcore.game.Version;

import java.util.ArrayList;
import java.util.List;

public class LibFilter {

    private static final String ASM_ALL_5_2_STRING = "{\n" +
            "      \"name\": \"org.ow2.asm:asm-all:5.2\",\n" +
            "      \"downloads\": {\n" +
            "        \"artifact\": {\n" +
            "          \"path\": \"org/ow2/asm/asm-all/5.2/asm-all-5.2.jar\",\n" +
            "          \"sha1\": \"2ea49e08b876bbd33e0a7ce75c8f371d29e1f10a\",\n" +
            "          \"url\": \"https://repo1.maven.org/maven2/org/ow2/asm/asm-all/5.2/asm-all-5.2.jar\"\n" +
            "        }\n" +
            "      }\n" +
            "    }";
    private static final String JNA_5_17_STRING = "{\n" +
            "      \"name\": \"net.java.dev.jna:jna:5.17.0\",\n" +
            "      \"downloads\": {\n" +
            "        \"artifact\": {\n" +
            "          \"path\": \"net/java/dev/jna/jna/5.17.0/jna-5.17.0.jar\",\n" +
            "          \"sha1\": \"5dc5682b4228c7efc0741fa12606ea4d3983a9e4\",\n" +
            "          \"url\": \"https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.17.0/jna-5.17.0.jar\"\n" +
            "        }\n" +
            "      }\n" +
            "    }";
    private static final String JNA_5_17_PLATFORM_STRING = "{\n" +
            "      \"name\": \"net.java.dev.jna:jna-platform:5.17.0\",\n" +
            "      \"downloads\": {\n" +
            "        \"artifact\": {\n" +
            "          \"path\": \"net/java/dev/jna/jna-platform/5.17.0/jna-platform-5.17.0.jar\",\n" +
            "          \"sha1\": \"3e188fef07cb6a9ac434ec30f59e6c622045bf13\",\n" +
            "          \"url\": \"https://repo1.maven.org/maven2/net/java/dev/jna/jna-platform/5.17.0/jna-platform-5.17.0.jar\"\n" +
            "        }\n" +
            "      }\n" +
            "    }";
    private static final String OSHI_6_8_1_STRING = "{\n" +
            "      \"name\": \"com.github.oshi:oshi-core:6.8.1\",\n" +
            "      \"downloads\": {\n" +
            "        \"artifact\": {\n" +
            "          \"path\": \"com/github/oshi/oshi-core/6.8.1/oshi-core-6.8.1.jar\",\n" +
            "          \"sha1\": \"dfd7f288123f92053c546373276100ff1c3302f5\",\n" +
            "          \"url\": \"https://repo1.maven.org/maven2/com/github/oshi/oshi-core/6.8.1/oshi-core-6.8.1.jar\"\n" +
            "        }\n" +
            "      }\n" +
            "    }";

    private static final Library ASM_ALL_5_2 = GSON.fromJson(ASM_ALL_5_2_STRING, Library.class);
    private static final Library JNA_5_17 = GSON.fromJson(JNA_5_17_STRING, Library.class);
    private static final Library JNA_5_17_PLATFORM = GSON.fromJson(JNA_5_17_PLATFORM_STRING, Library.class);
    private static final Library OSHI_6_8_1 = GSON.fromJson(OSHI_6_8_1_STRING, Library.class);

    public static Version filter(Version version) {
        return version.setLibraries(filterLibs(version.getLibraries()));
    }

    public static List<Library> filterLibs(List<Library> libraries) {
        ArrayList<Library> newLibraries = new ArrayList<>();
        for (Library library : libraries) {
            if (!library.getName().contains("org.lwjgl") && !library.getName().contains("jinput-platform") && !library.getName().contains("twitch-platform")) {
                String[] version = library.getName().split(":")[2].split("\\.");
                if (library.getArtifactId().equals("asm-all")) {
                    newLibraries.add(ASM_ALL_5_2);
                } else if (library.getName().startsWith("net.java.dev.jna:jna:")) {
                        newLibraries.add(JNA_5_17);
                } else if (library.getName().startsWith("net.java.dev.jna:jna-platform:")) {
                        newLibraries.add(JNA_5_17_PLATFORM);
                } else if (library.getName().startsWith("com.github.oshi:oshi-core:")) {
                        newLibraries.add(OSHI_6_8_1);
                   } else {
                    newLibraries.add(library);
                }
            }
        }
        return newLibraries;
    }

}
