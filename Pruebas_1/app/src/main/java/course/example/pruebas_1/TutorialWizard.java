package course.example.pruebas_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class TutorialWizard extends BasicWizardLayout {

    /**
     * Note that initially BasicWizardLayout inherits from {@link android.support.v4.app.Fragment} and therefore you must have an empty constructor
     */
    public TutorialWizard() {
        super();
    }

    //You must override this method and create a wizard flow by
    //using WizardFlow.Builder as shown in this example
    @Override
    public WizardFlow onSetup() {
        /* Optionally, you can set different labels for the control buttons
        setNextButtonLabel("Advance");
        setBackButtonLabel("Return");
        setFinishButtonLabel("Finalize"); */

        return new WizardFlow.Builder()
                .addStep(TutorialStep1.class)           //Add your steps in the order you want them
                .addStep(TutorialStep2.class)           //to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }
}