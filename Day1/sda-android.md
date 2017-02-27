# Android Studio Setup

- Download Android Studio 2.2.3 for Linux.

- Unpack the ZIP file in your home directory (for example, in
  `~/tools`).

- IMPORTANT: you need to delete the downloaded ZIP file now, because
  otherwise your disk space will be quickly exceeded, leading to
  errors later.

- Navigate to the `android-studio/bin` directory and execute
  `./studio.sh`

- The Wizard will guide you through the rest of the setup.

- Choose not to import settings from a previous installation.

- Choose "Standard" installation.

- The step "Downloading Components" will take a few minutes to
  complete.

- Unregistered VCS root detected -> ignore for now.

- "Help" -> "Edit Custom Properties..." then edit the config file by
  adding:
#---------------------------------------------------------------------
# IDEA can copy library .jar files to prevent their locking.
# By default this behavior is enabled on Windows and disabled on other platforms.
# Uncomment this property to override.
#---------------------------------------------------------------------
idea.jars.nocopy=false

- Build -> Clean Project
- Run

- Create New Virtual Device:
  - Nexus 5
  - Nougat, API Level 25, ABI x86
  - Click download (this step will take a few minutes!)

- Alternative:
  - Nexus 5
  - Marshmallow, API Level 23, x86
  - Click download (this step will take a few minutes!)
