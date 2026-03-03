# Bezel Fix

[English](#english) | [简体中文](#简体中文)

---

## 简体中文

### 项目简介
Bezel Fix 是一个专为低端设备优化的 Fabric 模组，旨在修复在使用 PojavLauncher 运行 Minecraft 1.21.11 时出现的**方块选择框**和 **F3 准心**渲染错误。除了核心修复功能外，它还提供了一系列增强视觉体验的可定制功能。

> [!IMPORTANT]
> **AI 辅助声明**：本项目的部分代码和文档是由 AI (Trae / Gemini) 辅助生成和优化的。

### 核心功能
- **修复渲染错误**：专门针对 PojavLauncher 在低端设备上的渲染兼容性问题，修复方块边缘选择框和 F3 调试准心的显示异常。
- **动态方块轮廓**：提供平滑的方块选择框移动动画，支持多种缓动函数（LINEAR, QUADIN, QUADOUT, QUADINOUT）。
- **高度可定制**：
    - 自定义填充与轮廓颜色（支持透明度）。
    - 调节轮廓粗细。
    - 自定义调试准心（Debug Cursor）的大小与厚度。
- **性能优化**：专为低端设备设计，确保在修复问题的同时不增加额外的性能负担。

### 安装要求
1. 安装 [Fabric Loader](https://fabricmc.net/)。
2. 下载最新版本的 Bezel Fix `.jar` 文件并放入 `mods` 文件夹。
3. **必要依赖**：
   - [Fabric API](https://modrinth.com/mod/fabric-api)
4. **可选推荐**：
   - [ModMenu](https://modrinth.com/mod/modmenu) (用于在游戏内直接修改配置)
   - [Cloth Config API](https://modrinth.com/mod/cloth-config) (用于配置界面)

### 构建方法

要从源代码构建 Bezel Fix 模组，请确保已克隆项目仓库并安装了 **JDK 21**。项目使用 Fabric Loom 和 Gradle 构建系统。

1. **准备环境**：

   * 安装 **JDK 21** 并设置 **JAVA\_HOME 环境变量**。

2. **开始构建**：

   * **Linux**：在**终端**中执行：

   ```bash
      ./gradlew build
      ```

   * **Windows**：在**cmd**中执行：

   ```cmd
      gradlew.bat build
      ```

3. **结束**:

   * 构建完成的jar文件存放在**build/libs**目录下。
---

## English

### Introduction
Bezel Fix is a Fabric mod optimized for low-end devices, specifically designed to fix **block selection box** and **F3 crosshair** rendering errors when running Minecraft 1.21.11 via PojavLauncher. Beyond core fixes, it provides various customizable features to enhance the visual experience.

> [!IMPORTANT]
> **AI Assisted Statement**: Parts of the code and documentation in this project were generated or optimized using AI (Trae / Gemini).

### Features
- **Rendering Fixes**: Targets rendering compatibility issues on PojavLauncher for low-end devices, fixing glitches in block outlines and the F3 debug crosshair.
- **Animated Outlines**: Smoothly animates the block selection box with various easing options (LINEAR, QUADIN, QUADOUT, QUADINOUT).
- **Highly Customizable**:
    - Custom fill and outline colors (with alpha support).
    - Adjustable outline thickness.
    - Customizable Debug Cursor size and thickness.
- **Performance Focused**: Designed for low-end hardware to ensure no performance degradation while fixing bugs.

### Installation
1. Install [Fabric Loader](https://fabricmc.net/).
2. Place the Bezel Fix `.jar` in your `mods` folder.
3. **Required Dependencies**:
   - [Fabric API](https://modrinth.com/mod/fabric-api)
4. **Recommended**:
   - [ModMenu](https://modrinth.com/mod/modmenu)
   - [Cloth Config API](https://modrinth.com/mod/cloth-config)

### Installation

1. Install \[Fabric Loader](https://fabricmc.net/).
2. Place the Bezel Fix `.jar` in your `mods` folder.
3. \*\*Required Dependencies\*\*:

   \* \[Fabric API](https://modrinth.com/mod/fabric-api)

4. \*\*Recommended\*\*:

   \* \[ModMenu](https://modrinth.com/mod/modmenu)
   \* \[Cloth Config API](https://modrinth.com/mod/cloth-config)

   ### Build

   To build a Bezel Fix mod from source, make sure you have cloned the project repository and installed JDK 21. The project uses Fabric Loom and Gradle to build the system

1. **Prepare the environment**:

   * Install **JDK 21** and set **JAVA\_HOME environment variables**.

2. **Start building**:

   * **Linux**：Execute in the **Shell**:

   ```bash
      ./gradlew build
      ```

   * **Windows**：Execute in the **cmd**:

   ```cmd
      gradlew.bat build
      ```

3. **End**:

   * The built jar file is stored in the build/libs directory.
---

### License
This project is licensed under MIT License. See the [LICENSE](LICENSE) file for details.