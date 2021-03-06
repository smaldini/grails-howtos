/* Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.doc.gradle

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.console.ConsoleCredentialsProvider
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

/**
 * Gradle task for committing changes in git.
 */
class GitPushTask extends DefaultTask {
    def repository
    def remote

    @InputDirectory File getRepository() { project.file(repository) }
    @Input @Optional String getRemote() { remote?.toString() }
    @Input @Optional List committer
    @Input @Optional List author

    @TaskAction
    def clone() {
        def cmd = Git.open(repository).push()
        cmd.credentialsProvider = new UsernamePasswordCredentialsProvider(
                project.getProperty("github.username"),
                project.getProperty("github.password"))
        cmd.remote = remote ?: "origin"
//        cmd.refSpecs = 
        cmd.call()
    }
}

