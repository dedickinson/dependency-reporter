import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter
import com.beust.jcommander.ParameterException
import com.beust.jcommander.IParameterValidator

class Args {
    @Parameter(names = ['--help', '-h'], description = 'Display help', help = true)
    private boolean help

    @Parameter(names = ['--gav'], description = '')
    String gav

    @Parameter(names = ['-g', '--group'], description = '')
    String group

    @Parameter(names = ['-a', '--artifact'], description = '')
    String artifact

    @Parameter(names = ['-v', '--version'], description = '')
    String varsion

    @Parameter(names = ['-n', '--name'], description = '')
    String name

    @Parameter(names = ['-d', '--description'], description = '')
    String description
}

new Args().with {
    try {
        JCommander jCmd = new JCommander(it, args)
        jCmd.programName = 'ceph-search'
        if (help) {
            jCmd.usage()
            return 0
        }
    } catch (com.beust.jcommander.ParameterException e) {
        println e.message
        return -1
    }
}
