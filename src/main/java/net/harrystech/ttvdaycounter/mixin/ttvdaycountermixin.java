package net.harrystech.ttvdaycounter.mixin;

import net.harrystech.ttvdaycounter.ttvdaycounter;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ttvdaycountermixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ttvdaycounter.LOGGER.info("TTVDayCounter has loaded!");
	}
}
