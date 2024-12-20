package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LichsMastery extends CardImpl {

    public LichsMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // You can't lose the game.
        this.addAbility(new SimpleStaticAbility(new LichsMasteryCantLoseEffect()));

        // Whenever you gain life, draw that many cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedGainedLifeValue.MANY),
                false, true
        ));

        // Whenever you lose life, for each 1 life you lost, exile a permanent you control or a card from your hand or graveyard.
        this.addAbility(new LoseLifeTriggeredAbility(new LichsMasteryLoseLifeEffect()));

        // When Lich's Mastery leaves the battlefield, you lose the game.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseGameSourceControllerEffect(), false));
    }

    private LichsMastery(final LichsMastery card) {
        super(card);
    }

    @Override
    public LichsMastery copy() {
        return new LichsMastery(this);
    }
}

class LichsMasteryCantLoseEffect extends ContinuousRuleModifyingEffectImpl {

    LichsMasteryCantLoseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false, false);
        staticText = "You can't lose the game";
    }

    private LichsMasteryCantLoseEffect(final LichsMasteryCantLoseEffect effect) {
        super(effect);
    }

    @Override
    public LichsMasteryCantLoseEffect copy() {
        return new LichsMasteryCantLoseEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}

class LichsMasteryLoseLifeEffect extends OneShotEffect {

    LichsMasteryLoseLifeEffect() {
        super(Outcome.Exile);
        this.staticText = "for each 1 life you lost, exile a permanent you control or a card from your hand or graveyard";
    }

    private LichsMasteryLoseLifeEffect(final LichsMasteryLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public LichsMasteryLoseLifeEffect copy() {
        return new LichsMasteryLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ControllerIdPredicate(controller.getId()));
        for (int i = 0; i < SavedLifeLossValue.MANY.calculate(game, source, this); i++) {
            int handCount = controller.getHand().size();
            int graveCount = controller.getGraveyard().size();
            int permCount = game.getBattlefield().getActivePermanents(filter, controller.getId(), game).size();
            if (graveCount + handCount == 0 || (permCount > 0 && controller.chooseUse(Outcome.Exile, "Exile permanent you control? (No = from hand or graveyard)", source, game))) {
                Target target = new TargetControlledPermanent(1, 1, new FilterControlledPermanent(), true);
                controller.choose(outcome, target, source, game);
                Effect effect = new ExileTargetEffect();
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
                effect.apply(game, source);
            } else if (graveCount == 0 || (handCount > 0 && controller.chooseUse(Outcome.Exile, "Exile a card from your hand? (No = from graveyard)", source, game))) {
                Target target = new TargetCardInHand(1, 1, new FilterCard());
                controller.choose(outcome, target, source, game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.EXILED, source, game);
                }
            } else {
                Target target = new TargetCardInYourGraveyard(1, 1, new FilterCard(), true);
                target.choose(Outcome.Exile, source.getControllerId(), source.getSourceId(), source, game);
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.EXILED, source, game);
                }
            }
        }
        return true;
    }

}
