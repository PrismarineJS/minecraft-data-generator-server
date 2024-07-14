# minecraft-data-generator-server

This tool generates minecraft-data files based on a running server using a fabric mod.
The supported versions can be read in the directory structure.
Every version has its own directory.

## Usage

You can put any version that has a directory into the command below.
Just replace `<version>` with the version you want to generate.

For Linux/Mac OS:

```bash
./gradlew :<version>:runServer
```

For Windows:

```bash
gradlew.bat :<version>:runServer
```

You can then find the minecraft-data in the `<version>/run/minecraft-data` directory.

## Adding a new version

To add a new version, you need to create a new directory in the root directory with the version name.
Then you need to add the version to the list in the `settings.gradle.kts` file.
Then also add it to the `.github/workflows/build.yml` file.
Then copy the code of the most recently released version into the new directory.
Then you need to change the values in the `gradle.properties` file.
At last, you need to fix all code issues that are caused by the new version.
For that, use an IDE like IntelliJ IDEA to fix the issues.
Once everything compiles, you can commit the changes, push them to your fork and create a pull request.
Once your PR was accepted and merged, the new version will be available in the next release.
