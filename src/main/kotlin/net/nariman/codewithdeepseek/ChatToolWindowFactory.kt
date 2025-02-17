package net.nariman.codewithdeepseek

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory

class ChatToolWindowFactory: ToolWindowFactory {
    @Override
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val chatToolWindow = ChatPanel()
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(chatToolWindow, "Chat With Deepseek", false)
        toolWindow.contentManager.addContent(content)
    }
}