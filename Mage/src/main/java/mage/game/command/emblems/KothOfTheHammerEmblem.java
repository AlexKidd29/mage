package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 * @author spjspj
 */
public final class KothOfTheHammerEmblem extends Emblem {
    // "Mountains you control have '{T}: This land deals 1 damage to any target.'"

    public KothOfTheHammerEmblem() {
        super("Emblem Koth");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new KothOfTheHammerThirdEffect()));
    }

    private KothOfTheHammerEmblem(final KothOfTheHammerEmblem card) {
        super(card);
    }

    @Override
    public KothOfTheHammerEmblem copy() {
        return new KothOfTheHammerEmblem(this);
    }
}

class KothOfTheHammerThirdEffect extends ContinuousEffectImpl {

    static final FilterLandPermanent mountains = new FilterLandPermanent("Mountain you control");

    static {
        mountains.add(SubType.MOUNTAIN.getPredicate());
        mountains.add(TargetController.YOU.getControllerPredicate());
    }

    public KothOfTheHammerThirdEffect() {
        super(Duration.EndOfGame, Outcome.AddAbility);
        staticText = "Mountains you control have '{T}: This land deals 1 damage to any target.'";
    }

    protected KothOfTheHammerThirdEffect(final KothOfTheHammerThirdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                if (sublayer == SubLayer.NA) {
                    for (Permanent permanent : game.getBattlefield().getActivePermanents(mountains, source.getControllerId(), source, game)) {
                        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
                        ability.addTarget(new TargetAnyTarget());
                        permanent.addAbility(ability, source.getSourceId(), game);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KothOfTheHammerThirdEffect copy() {
        return new KothOfTheHammerThirdEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
