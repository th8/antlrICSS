package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

public class Fixtures {

    public static AST uncheckedLevel0() {
		Stylesheet stylesheet = new Stylesheet();
		/*
		p {
			background-color: #ffffff;
			width: 500px;
		}
		*/
		stylesheet.addChild((new Stylerule())
				.addChild(new TagSelector("p"))
				.addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
				.addChild((new Declaration("width"))
						.addChild(new PixelLiteral("500px")))
		);
		/*
		a {
			color: #ff0000;
		}
		*/
		stylesheet.addChild((new Stylerule())
				.addChild(new TagSelector("a"))
				.addChild((new Declaration("color"))
						.addChild(new ColorLiteral("#ff0000")))
		);
		/*
		#menu {
			width: 520px;
		}
		*/
		stylesheet.addChild((new Stylerule())
				.addChild(new IdSelector("#menu"))
				.addChild((new Declaration("width"))
						.addChild(new PixelLiteral("520px")))
		);
		/*
		.menu {
			color: #000000;
		}
		*/
		stylesheet.addChild((new Stylerule())
				.addChild(new ClassSelector(".menu"))
				.addChild((new Declaration("color"))
						.addChild(new ColorLiteral("#000000")))
		);

        return new AST(stylesheet);
    }

	public static AST uncheckedLevel1() {
		Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 500px;
		 */
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
            .addChild(new TagSelector("p"))
            .addChild((new Declaration("background-color"))
                    .addChild(new ColorLiteral("#ffffff")))
            .addChild((new Declaration("width"))
                    .addChild(new VariableReference("ParWidth")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new TagSelector("a"))
			.addChild((new Declaration("color"))
				.addChild(new VariableReference("LinkColor")))
		);
        /*
            #menu {
	            width: 520px;
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new IdSelector("#menu"))
			.addChild((new Declaration("width"))
				.addChild(new PixelLiteral("520px")))
		);
        /*
            .menu {
	            color: #000000;
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new ClassSelector(".menu"))
			.addChild((new Declaration("color"))
				.addChild(new ColorLiteral("#000000")))
		);
		return new AST(stylesheet);
	}

	public static AST uncheckedLevel2() {
		Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 500px;
		 */
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
            .addChild(new TagSelector("p"))
            .addChild((new Declaration("background-color"))
                    .addChild(new ColorLiteral("#ffffff")))
            .addChild((new Declaration("width"))
                    .addChild(new VariableReference("ParWidth")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new TagSelector("a"))
			.addChild((new Declaration("color"))
				.addChild(new VariableReference("LinkColor")))
		);
        /*
            #menu {
        	width: ParWidth + 2 * 10px;
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new IdSelector("#menu"))
			.addChild((new Declaration("width"))
				.addChild((new AddOperation())
                        .addChild(new VariableReference("ParWidth"))
                        .addChild((new MultiplyOperation())
                                .addChild(new ScalarLiteral("2") )
                                .addChild(new PixelLiteral("10px"))

        ))));
        /*
            .menu {
	            color: #000000;
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new ClassSelector(".menu"))
			.addChild((new Declaration("color"))
				.addChild(new ColorLiteral("#000000")))
		);
		return new AST(stylesheet);
	}

	public static AST uncheckedLevel3() {
		Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 100px;
		 */
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
		stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("100px"))
        );
   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
            .addChild(new TagSelector("p"))
            .addChild((new Declaration("background-color"))
                    .addChild(new ColorLiteral("#ffffff")))
            .addChild((new Declaration("width"))
                    .addChild(new VariableReference("ParWidth")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new TagSelector("a"))
			.addChild((new Declaration("color"))
				.addChild(new VariableReference("LinkColor"))
            )
		);
        /*
            #menu {
        	width: ParWidth + 20px;
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new IdSelector("#menu"))
			.addChild((new Declaration("width"))
				.addChild((new AddOperation())
                        .addChild(new VariableReference("ParWidth"))
                        .addChild(new PixelLiteral("20px"))
                )
            )
        );
        /*
            .menu {
	            color: #000000;
	            LinkColor := #0000ff;
	            a {
	                background-color: LinkColor;
	            }
            }
        */
        stylesheet.addChild((new Stylerule())
			.addChild(new ClassSelector(".menu"))

			.addChild((new Declaration("color"))
				.addChild(new ColorLiteral("#000000"))
            )
            .addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#0000ff"))
            )
            .addChild((new Stylerule())
                .addChild(new TagSelector("a"))
                .addChild((new Declaration("background-color"))
                    .addChild(new VariableReference("LinkColor"))
                )
            )
		);

		return new AST(stylesheet);
	}
}
