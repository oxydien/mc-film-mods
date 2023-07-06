package dev.oxydien.mbtym.gui;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.CheckboxComponent;
import io.wispforest.owo.ui.component.DiscreteSliderComponent;
import io.wispforest.owo.ui.component.SliderComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.util.Identifier;


public class DebugScreen extends BaseUIModelScreen<FlowLayout> {
	public DebugScreen() {
		super(FlowLayout.class,DataSource.asset(new Identifier(InitMod.MOD_ID,"debug_ui")));
	}

	@Override
	protected void build(FlowLayout rootComponent) {
		var debugCheckbox = rootComponent.childById(CheckboxComponent.class, "debug-checkbox");
		var closeButton = rootComponent.childById(ButtonComponent.class, "close-button");
		var carSpeedSlider = rootComponent.childById(DiscreteSliderComponent.class,"car-speed");
		var carSpeedDefault = rootComponent.childById(ButtonComponent.class,"car-speed-default");
		var carAccelerationSlider = rootComponent.childById(DiscreteSliderComponent.class,"car-acceleration");
		var carAccelerationDefault = rootComponent.childById(ButtonComponent.class,"car-acceleration-default");
		assert debugCheckbox != null;
		assert closeButton != null;
		assert carSpeedSlider != null;
		assert carSpeedDefault != null;
		assert carAccelerationSlider != null;
		assert carAccelerationDefault != null;
		debugCheckbox.checked(InitMod.DoDebug);
		carSpeedSlider.setFromDiscreteValue(TestCarEntity.GetMaxSpeed());
		carAccelerationSlider.setFromDiscreteValue(TestCarEntity.GetAcceleration());
		debugCheckbox.onChanged(checked -> {
			InitMod.DoDebug = checked;
			InitMod.Log(checked ? "Doing debug" : "Not doing debug");
		});
		carSpeedSlider.onChanged().subscribe(value -> {
			TestCarEntity.SetMaxSpeed(value);
			carSpeedSlider.setFromDiscreteValue(TestCarEntity.GetMaxSpeed());
		});
		carSpeedDefault.onPress(click -> {
			TestCarEntity.SetMaxSpeed(TestCarEntity.DEFAULT_MAX_SPEED);
			carSpeedSlider.setFromDiscreteValue(TestCarEntity.DEFAULT_MAX_SPEED);
		});
		carAccelerationSlider.onChanged().subscribe(value -> {
			TestCarEntity.SetAcceleration(value);
			carAccelerationSlider.setFromDiscreteValue(TestCarEntity.GetAcceleration());
		});
		carAccelerationDefault.onPress(click -> {
			TestCarEntity.SetAcceleration(TestCarEntity.DEFAULT_ACCELERATION);
			carAccelerationSlider.setFromDiscreteValue(TestCarEntity.DEFAULT_ACCELERATION);
		});
		closeButton.onPress(press -> {
			this.closeScreen();
		});
	}
}
