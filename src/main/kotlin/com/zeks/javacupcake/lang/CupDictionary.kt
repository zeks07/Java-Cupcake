package com.zeks.javacupcake.lang

object CupDictionary {
    @JvmStatic
    val PACKAGE = "package"
    @JvmStatic
    val IMPORT = "import"
    @JvmStatic
    val CODE = "code"
    @JvmStatic
    val ACTION = "action"
    @JvmStatic
    val PARSER = "parser"
    @JvmStatic
    val WITH = "with"
    @JvmStatic
    val INIT = "init"
    @JvmStatic
    val SCAN = "scan"
    @JvmStatic
    val TERMINAL = "terminal"
    @JvmStatic
    val NON = "non"
    @JvmStatic
    val NONTERMINAL = "nonterminal"
    @JvmStatic
    val PRECEDENCE = "precedence"
    @JvmStatic
    val LEFT = "left"
    @JvmStatic
    val RIGHT = "right"
    @JvmStatic
    val NONASSOC = "nonassoc"
    @JvmStatic
    val START = "start"
    @JvmStatic
    val PERCENT_PREC = "%prec"

    @JvmStatic
    val LINE_START_KEYWORDS = arrayOf(
        PACKAGE, IMPORT, ACTION, PARSER, INIT, SCAN, TERMINAL, NON, NONTERMINAL, PRECEDENCE, START)

    @JvmStatic
    val KEYWORDS = arrayOf(
        PACKAGE, IMPORT, CODE, ACTION, PARSER, WITH, INIT, SCAN, TERMINAL, NON, NONTERMINAL, PRECEDENCE, LEFT, RIGHT, NONASSOC, START, PERCENT_PREC
    )
}