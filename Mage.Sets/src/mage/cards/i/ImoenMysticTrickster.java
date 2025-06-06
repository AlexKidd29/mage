package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.InitiativeHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImoenMysticTrickster extends CardImpl {

    public ImoenMysticTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of your end step, if you have the initiative, draw a card. Draw another card if you've completed a dungeon.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new DrawCardSourceControllerEffect(1),
                false, HaveInitiativeCondition.instance
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), CompletedDungeonCondition.instance,
                "Draw another card if you've completed a dungeon"
        ));
        ability.addHint(InitiativeHint.instance);
        this.addAbility(ability.addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private ImoenMysticTrickster(final ImoenMysticTrickster card) {
        super(card);
    }

    @Override
    public ImoenMysticTrickster copy() {
        return new ImoenMysticTrickster(this);
    }
}
