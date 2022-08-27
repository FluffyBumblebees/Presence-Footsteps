package eu.ha3.presencefootsteps;

import java.util.*;

import eu.ha3.presencefootsteps.sound.SoundEngine;
import eu.ha3.presencefootsteps.world.Emitter;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class PFDebugHud {

    private final SoundEngine engine;

    PFDebugHud(SoundEngine engine) {
        this.engine = engine;
    }

    public void render(HitResult blockHit, HitResult fluidHit, List<String> list) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (blockHit.getType() == HitResult.Type.BLOCK) {
            BlockState state = client.world.getBlockState(((BlockHitResult)blockHit).getBlockPos());


            renderSoundList("Primitive: " + state.getSoundGroup().getStepSound().getId(),
                    engine.getIsolator().getPrimitiveMap().getAssociations(state.getSoundGroup()),
                    list);

            renderSoundList("PF Sounds",
                    engine.getIsolator().getBlockMap().getAssociations(state),
                    list);
            BlockSoundGroup sound = state.getSoundGroup();
            
            renderSoundList("PF Prims",
                    engine.getIsolator().getPrimitiveMap().getAssociations(sound),
                    list);
        }

        if (client.targetedEntity != null) {
            renderSoundList("PF Golem Sounds",
                    engine.getIsolator().getGolemMap().getAssociations(client.targetedEntity.getType()),
                    list);
            list.add(engine.getIsolator().getLocomotionMap().lookup(client.targetedEntity).getDisplayName());
        }
    }

    private void renderSoundList(String title, Map<String, String> sounds, List<String> list) {
        list.add("");
        list.add(title);
        if (sounds.isEmpty()) {
            list.add(Emitter.UNASSIGNED);
        } else {
            sounds.forEach((key, value) -> {
                list.add((key.isEmpty() ? "default" : key) + ": " + value);
            });
        }
    }
}
