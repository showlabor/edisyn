/***
    Copyright 2017 by Sean Luke
    Licensed under the Apache License version 2.0
*/

package edisyn.synth;

import edisyn.*;
import edisyn.gui.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
   A patch editor for the Kawai K4.
        
   @author Sean Luke
*/

public class KawaiK4 extends Synth
    {
    /// Various collections of parameter names for pop-up menus
        
    public static final String[] CHANNELS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
    public static final String[] BANKS = { "A", "B", "C", "D", "Ext. A", "Ext. B", "Ext. C", "Ext. D" };
    public static final String[] WAVES = { "Sin 1st", "Sin 2nd", "Sin 3rd", "Sin 4th", "Sin 5th", "Sin 6th", "Sin 7th", "Sin 8th", "Sin 9th", 
                                           "Saw 1", "Saw 2", "Saw 3", "Saw 4", "Saw 5", "Saw 6", "Saw 7", "Saws", 
                                           "Pulse", "Triangle", "Square", "Rectangular 1", "Rectangular 2", "Rectangular 3", "Rectangular 4", "Rectangular 5", "Rectangular 6", 
                                           "Pure Horn L", "Punch Brass 1", "Oboe 1", "Oboe 2", "Classic Grand", 
                                           "Electric Piano 1", "Electric Piano 2", "Electric Piano 3", "Electric Organ 1", "Electric Organ 2", 
                                           "Positif", "Electric Organ 3", "Electric Organ 4", "Electric Organ 5", "Electric Organ 6", "Electric Organ 7", 
                                           "Electric Organ 8", "Electric Organ 9", "Classic Guitar", "Steel Strings", "Harp", "Wood Bass", "Synth Bass 3", 
                                           "Digibass", "Finger Bass", "Marimba", "Synth Voice", "Glass Harp 1", "Cello", "Xylophone", "Electric Piano 4", 
                                           "Synclavier M", "Electric Piano 5", "Electric Organ 10", "Electric Organ 11", "Electric Organ 12", "Big Pipe", 
                                           "Glass Harp 2", "Random", "Electric Piano 6", "Synth Bass 4", "Synth Bass 1", "Synth Bass 2", "Quena", "Oboe 3", 
                                           "Pure Horn H", "Fat Brass", "Punch Brass 2", "Electric Piano 7", "Electric Piano 8", "Synclavier 2", 
                                           "Harpsichord M", "Harpsichord L", "Harpsichord H", "Electric Organ 13", "Koto", "Sitar L", "Sitar H", 
                                           "Pick Bass", "Synth Bass 5", "Synth Bass 6", "Vibraphone Attack", "Vibraphone 1", "Horn Vibe", 
                                           "Steel Drum 1", "Steel Drum 2", "Vibraphone 2", "Marimba Attack", "Harmonica", "Synth", "Kick", 
                                           "Gated Kick", "Snare Tite", "Snare Deep", "Snare Hi", "Rim Snare", "Rim Shot", "Tom", "Tom VR", 
                                           "Electric Tom", "High Hat Closed", "High Hat Open", "High Hatopen VR", "High Hat Foot", "Crash", "Crash VR", "Crash VR 2",
                                           "Ride Edge", "Ride Edge VR", "Ride Cup", "Ride Cup VR", "Claps", "Cowbell", "Conga", "Conga Slap", 
                                           "Tambourine", "Tambourine VR", "Claves", "Timbale", "Shaker", "Shaker VR", "Timpani", "Timpani VR", 
                                           "Sleighbell", "Bell", "Metal Hit", "Click", "Pole", "Glocken", "Marimba", "Piano Attack", "Water Drop", 
                                           "Char", "Piano Normal", "Piano VR", "Cello Normal", "Cello VR 1", "Cello VR 2", "Cello 1-Shot", 
                                           "Strings Normal", "Strings VR", "Slap Bass L Normal", "Slap Bass L VR", "Slap Bass L 1-Shot", 
                                           "Slap Bass H Normal", "Slap Bass H VR", "Slap Bass H 1-Shot", "Pick Bass Normal", "Pick Bass VR", 
                                           "Pick Bass 1-Shot", "Wood Bass Attack", "Wood Bass Normal", "Wood Bass VR", "Fretless Normal", 
                                           "Fretless VR", "Synth Bass Normal", "Synth Bass VR", "Electric Guitar Mute Normal", 
                                           "Electric Guitar Mute VR", "Electric Guitar Mute 1-Shot", "Dist Mute Normal", "Dist Mute VR", 
                                           "Dist Mute 1-Shot", "Dist Lead Normal", "Dist Lead VR", "Electric Guitar Normal", "Gut Guitar Normal", 
                                           "Gut Guitar VR", "Gut Guitar 1-Shot", "Flute Normal", "Flute 1-Shot", "Bottle Blow Normal", 
                                           "Bottle Blow VR", "Sax Normal", "Sax VR 1", "Sax VR 2", "Sax 1-Shot", "Trumpet Normal", 
                                           "Trumpet VR 1", "Trumpet VR 2", "Trumpet 1-Shot", "Trombone Normal", "Trombone VR", 
                                           "Trombone 1-Shot", "Voice", "Noise", "Piano 1", "Piano 2", "Piano 3", "Piano 4", "Pianos", 
                                           "Cello 1", "Cello 2", "Cello 3", "Cello 4", "Cello 5", "Cello 6", "Strings 1", "Strings 2", 
                                           "Slap Bass L", "Slap Bass L 1-Shot", "Slap Bass H", "Slap Bass H 1-Shot", "Pick Bass 1", 
                                           "Pick Bass 2 1-Shot", "Pick Bass 3 1-Shot", "Electric Guitar Mute", "Electric Guitar Mute 1-Shot", 
                                           "Dist Lead 1", "Dist Lead 2", "Dist Lead 3", "Gut Guitar 1", "Gut Guitar 2", "Gut Guitar 3 1-Shot", 
                                           "Gut Guitar 4 1-Shot", "Flute 1", "Flute 2", "Sax 1", "Sax 2", "Sax 3", "Sax 4 1-Shot", "Sax 5 1-Shot", 
                                           "Sax 6 1-Shot", "Trumpet", "Trumpet 1-Shot", "Voice 1", "Voice 2", "Reverse 1", "Reverse 2", 
                                           "Reverse 3", "Reverse 4", "Reverse 5", "Reverse 6", "Reverse 7", "Reverse 8", "Reverse 9", 
                                           "Reverse 10", "Reverse 11", "Loop 1", "Loop 2", "Loop 3", "Loop 4", "Loop 5", "Loop 6", 
                                           "Loop 7", "Loops", "Loop 9", "Loop 10", "Loop 11", "Loop 12"};
    public static final String[] KS_CURVES = { "Linear", "Exponential", "Logarithmic", "Ramped", "Spit", "Triangle", "Late", "Early" };
    public static final String[] VELOCITY_CURVES = { "Linear", "Logarithmic", "Exponential", "Exponential Strong", "Linear Then Off", "Off Then Linear", "Slow Middle", "Fast Middle" };
    public static final String[] KEYS = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    public static final String[] LFO_SHAPES = new String[] { "Triangle", "Sawtooth", "Square", "Random" };
    public static final String[] SOURCE_MODES = new String[] { "Normal", "Twin", "Double" };
    public static final String[] POLY_MODES = new String[] { "Poly 1", "Poly 2", "Solo 1", "Solo 2" };
    public static final String[] OUT_SELECTS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
    public static final String[] WHEEL_ASSIGNMENTS = new String[] { "Vibrato", "LFO", "Filter" };

    public KawaiK4()
        {
        for(int i = 0; i < allParameters.length; i++)
            {
            allParametersToIndex.put(allParameters[i], Integer.valueOf(i));
            }
        
        setSendsAllParametersInBulk(true);
        
        /// SOUND PANEL
                
        JComponent soundPanel = new SynthPanel();
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.add(addNameGlobal(Style.COLOR_GLOBAL));
        hbox.addLast(addSourceGlobal(Style.COLOR_A));
        vbox.add(hbox);
        
        vbox.add(addLFO(Style.COLOR_B));
        
        hbox = new HBox();
        hbox.add(addVibrato(Style.COLOR_C));
        hbox.addLast(addAutoBend(Style.COLOR_C));
        vbox.add(hbox);
        
        soundPanel.add(vbox, BorderLayout.CENTER);
        tabs.addTab("Global", soundPanel);
                

        JComponent sourcePanel = new SynthPanel();
        vbox = new VBox();
        
        vbox.add(addSource(1, Style.COLOR_A));
        vbox.add(addEnvelope(1, Style.COLOR_B));

        vbox.add(addSource(2, Style.COLOR_A));
        vbox.add(addEnvelope(2, Style.COLOR_B));

        sourcePanel.add(vbox, BorderLayout.CENTER);
        tabs.addTab("Sources 1-2", sourcePanel);

        sourcePanel = new SynthPanel();
        vbox = new VBox();
        
        vbox.add(addSource(3, Style.COLOR_A));
        vbox.add(addEnvelope(3, Style.COLOR_B));

        vbox.add(addSource(4, Style.COLOR_A));
        vbox.add(addEnvelope(4, Style.COLOR_B));

        sourcePanel.add(vbox, BorderLayout.CENTER);
        tabs.addTab("Sources 3-4", sourcePanel);
        

        JComponent filterPanel = new SynthPanel();
        vbox = new VBox();
        
        vbox.add(addFilter(1, Style.COLOR_A));
        vbox.add(addFilterEnvelope(1, Style.COLOR_B));

        vbox.add(addFilter(2, Style.COLOR_A));
        vbox.add(addFilterEnvelope(2, Style.COLOR_B));

        filterPanel.add(vbox, BorderLayout.CENTER);
        tabs.addTab("Filters", filterPanel);

        tabs.addTab("About", new HTMLBrowser(this.getClass().getResourceAsStream("KawaiK4.html")));

        model.set("name", "Untitled  ");  // has to be 10 long
        
        loadDefaults();        
        }
                
                
    public JFrame sprout()
        {
        JFrame frame = super.sprout();
        // We can't request the current working memory (don't ask why)
        receiveCurrent.setEnabled(false);
        return frame;
        }         

    public String getDefaultResourceFileName() { return "KawaiK4.init"; }

    public boolean gatherInfo(String title, Model change, boolean writing)
        {
        JComboBox channel = new JComboBox(CHANNELS);
        channel.setSelectedIndex(model.get("channel", 0));
        

        JComboBox bank = new JComboBox(BANKS);
        bank.setSelectedIndex(model.get("bank", 0));
        
        JTextField number = new JTextField("" + (model.get("number", 0) + 1), 3);

        while(true)
            {
            boolean result = doMultiOption(this, new String[] { "Channel", "Bank", "Patch Number"}, 
                new JComponent[] { channel, bank, number }, title, "Enter the Channel, Bank, and Patch number");
                
            if (result == false) 
                return false;
                                
            int n;
            try { n = Integer.parseInt(number.getText()); }
            catch (NumberFormatException e)
                {
                JOptionPane.showMessageDialog(null, "The Patch Number must be an integer 1...16", title, JOptionPane.ERROR_MESSAGE);
                continue;
                }
            if (n < 1 || n > 16)
                {
                JOptionPane.showMessageDialog(null, "The Patch Number must be an integer 1...16", title, JOptionPane.ERROR_MESSAGE);
                continue;
                }
                
            n--;
                                
            int i = bank.getSelectedIndex();
            int c = channel.getSelectedIndex();
                        
                        
            change.set("bank", i);
            change.set("number", n);
            change.set("channel", c);
                        
            return true;
            }
        }

    /** Add the global patch category (name, id, number, etc.) */
    public JComponent addNameGlobal(Color color)
        {
        Category globalCategory = new Category(getSynthName(), color);
                
        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
                
        VBox vbox = new VBox();
        HBox hbox2 = new HBox();
        comp = new PatchDisplay(this, "Patch", "bank", "number", 4)
            {
            public String numberString(int number) { number += 1; return (number > 9 ? "0" : "00") + number; }
            public String bankString(int bank) { return BANKS[bank]; }
            };
        hbox2.add(comp);
        comp = new PatchDisplay(this, "Channel", "channel", null, 3)
            {
            public String numberString(int number) { return "" + (number + 1); }
            };
        hbox2.add(comp);
        vbox.add(hbox2);
        
        comp = new StringComponent("Patch Name", this, "name", 10, "Name must be up to 10 ASCII characters.")
            {
            public boolean isValid(String val)
                {
                if (val.length() > 10) return false;
                for(int i = 0 ; i < val.length(); i++)
                    {
                    char c = val.charAt(i);
                    if (c < 32 || c > 127) return false;
                    }
                return true;
                }
                                
            public void update(String key, Model model)
                {
                super.update(key, model);
                updateTitle();
                }
            };
        model.setImmutable("name", true);
        vbox.addBottom(comp);  // doesn't work right :-(
        hbox.add(vbox);

        globalCategory.add(hbox, BorderLayout.WEST);
        return globalCategory;
        }


    public JComponent addSourceGlobal( Color color)
        {
        Category category = new Category("Sources Global", color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
        
        VBox vbox = new VBox();
        params = SOURCE_MODES;
        comp = new Chooser("Source Mode", this, "sourcemode", params);
        vbox.add(comp);

        params = POLY_MODES;
        comp = new Chooser("Poly Mode", this, "polymode", params);
        vbox.add(comp);
        hbox.add(vbox);
        
        vbox = new VBox();
        params = WHEEL_ASSIGNMENTS;
        comp = new Chooser("Mod Wheel", this, "wheelassign", params);
        vbox.add(comp);

        comp = new CheckBox("AM S1 -> S2", this, "ams1>s2");
        ((CheckBox)comp).addToWidth(1);
        vbox.add(comp);

        comp = new CheckBox("AM S3 -> S4", this, "ams3>s4");
        vbox.add(comp);
        hbox.add(vbox);
                
        comp = new LabelledDial("Volume", this, "volume", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Effect", this, "effect", color, 0, 31, -1);
        hbox.add(comp);

        comp = new LabelledDial("Out Select", this, "outselect", color, 0, 7)
            {
            public String map(int val)
                {
                return OUT_SELECTS[val];
                }
            };
        hbox.add(comp);

        comp = new LabelledDial("Pitch Bend", this, "pitchbend", color, 0, 12);
        hbox.add(comp);

        // this appears to be poorly named in the manual (page 36)
        comp = new LabelledDial("Pressure", this, "pres>freq", color, 0, 100, 50);
        hbox.add(comp);

        category.add(hbox, BorderLayout.CENTER);
        return category;
        }



    public JComponent addVibrato(Color color)
        {
        Category category = new Category("Vibrato", color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
        
        VBox vbox = new VBox();
        params = LFO_SHAPES;  // also vibrato
        comp = new Chooser("Shape", this, "vibshape", params);
        vbox.add(comp);
        hbox.add(vbox);
                
        comp = new LabelledDial("Speed", this, "vibspeed", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Depth", this, "vibratodep", color, 0, 100, 50);
        hbox.add(comp);

        comp = new LabelledDial("Pressure", this, "vibprs>vib", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        category.add(hbox, BorderLayout.CENTER);
        return category;
        }

    public JComponent addAutoBend(Color color)
        {
        Category category = new Category("Auto Bend", color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
        
        comp = new LabelledDial("Time", this, "autobendtime", color, 0, 100, 50);
        hbox.add(comp);
        
        comp = new LabelledDial("Depth", this, "autobenddepth", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Key Scaling", this, "autobendks>time", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Velocity", this, "autobendvel>dep", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        category.add(hbox, BorderLayout.CENTER);
        return category;
        }

    public JComponent addLFO(Color color)
        {
        Category category = new Category("LFO ", color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
        
        VBox vbox = new VBox();
        params = LFO_SHAPES;
        comp = new Chooser("Shape", this, "lfoshape", params);
        vbox.add(comp);
        hbox.add(vbox);

        comp = new LabelledDial("Speed", this, "lfospeed", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Delay", this, "lfodelay", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Depth", this, "lfodep", color, 0, 100, 50);
        hbox.add(comp);
        
        comp = new LabelledDial("Pressure", this, "lfoprs>dep", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        category.add(hbox, BorderLayout.CENTER);
        return category;
        }


    public JComponent addSource(int src, Color color)
        {
        Category category = new Category("Source " + src, color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
        
        VBox vbox = new VBox();
        params = WAVES;
        comp = new Chooser("Wave", this, "s" + src + "waveselect", params);
        vbox.add(comp);
                
        // Normally this is in global, but I think it makes more sense here
        comp = new CheckBox("Mute", this, "s" + src + "mute");
        vbox.add(comp);
        hbox.add(vbox);

        vbox = new VBox();
        params = KS_CURVES;
        comp = new Chooser("Key Scaling Curve", this, "s" + src + "kscurve", params);
        vbox.add(comp);

        params = KS_CURVES;
        comp = new Chooser("Velocity Curve", this, "s" + src + "velcurve", params);
        vbox.add(comp);
        hbox.add(vbox);
                
        vbox = new VBox();
        comp = new CheckBox("Keytrack", this, "s" + src + "keytrack");
        vbox.add(comp);

        comp = new CheckBox("Pressure -> Pitch", this, "s" + src + "prs>frqsw");
        vbox.add(comp);

        comp = new CheckBox("Vibrato/Auto Bend -> Pitch", this, "s" + src + "vib/a.bendsw");
        vbox.add(comp);
        hbox.add(vbox);
                
        comp = new LabelledDial("Delay", this, "s" + src + "delay", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Coarse", this, "s" + src + "coarse", color, 0, 48, 24)
            {
            public boolean isSymmetric() { return true; }
            };
        hbox.add(comp);

        comp = new LabelledDial("Fix", this, "s" + src + "fix", color, 0, 115)
            {
            public String map(int val)
                {
                return KEYS[val % 12] + (val / 12 + 1);  // note integer division
                }
            };
        hbox.add(comp);

        comp = new LabelledDial("Fine", this, "s" + src + "fine", color, 0, 100, 50);
        hbox.add(comp);

        // yeah, I know this should be in the DCA, but it
        // makes the window smaller here.  Also src = envelope so we're okay
        comp = new LabelledDial("Level", this, "s" + src + "envelopelevel", color, 0, 100);
        hbox.add(comp);


        category.add(hbox, BorderLayout.CENTER);
        return category;
        }
                
    /** Add add a DCA category */
    public JComponent addEnvelope(int envelope, Color color)
        {
        Category category = new Category("Envelope " + envelope, color);

        JComponent comp;
        String[] params;
        HBox hbox = new HBox();
               
        comp = new LabelledDial("Attack", this, "s" + envelope + "envelopeattack", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Decay", this, "s" + envelope + "envelopedecay", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Sustain", this, "s" + envelope + "envelopesustain", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Release", this, "s" + envelope + "enveloperelease", color, 0, 100);
        hbox.add(comp);
        
        comp = new LabelledDial("Velocity", this, "s" + envelope + "levelmodvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Pressure", this, "s" + envelope + "levelmodprs", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Key Scaling", this, "s" + envelope + "levelmodks", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Velocity", this, "s" + envelope + "timemodonvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Release Vel", this, "s" + envelope + "timemodoffvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);

        comp = new LabelledDial("Key Scaling", this, "s" + envelope + "timemodks", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);

        // ADSR
        comp = new EnvelopeDisplay(this, Color.red, 
            new String[] { null, "s" + envelope + "envelopeattack", "s" + envelope + "envelopedecay", null, "s" + envelope + "enveloperelease" },
            new String[] { null, null, "s" + envelope + "envelopesustain", "s" + envelope + "envelopesustain", null },
            new double[] { 0, 0.25/100.0, 0.25 / 100.0,  0.25, 0.25/100.0},
            new double[] { 0, 1.0, 1.0 / 100.0, 1.0/100.0, 0 });
        hbox.addLast(comp);
                
        category.add(hbox, BorderLayout.CENTER);
        return category;
        }
        

    public JComponent addFilter(int filter, Color color)
        {
        Category category = new Category("Filter " + filter, color);
                
        JComponent comp;
        String[] params;
        final HBox hbox = new HBox();

        comp = new LabelledDial("Cutoff", this,  "f" + filter + "cutoff", color, 0, 100);
        hbox.add(comp);
                
        comp = new LabelledDial("Resonance", this,  "f" + filter + "resonance", color, 0, 7, -1);
        hbox.add(comp);

        comp = new LabelledDial("Velocity", this,  "f" + filter + "cutoffmodks", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Cutoff Mod");
        hbox.add(comp);
                
        comp = new LabelledDial("Pressure", this,  "f" + filter + "cutoffmodprs", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Cutoff Mod");
        hbox.add(comp);
                
        comp = new LabelledDial("Key Scaling", this,  "f" + filter + "cutoffmodvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Cutoff Mod");
        hbox.add(comp);
                  
        comp = new LabelledDial("Velocity", this, "f" + filter + "dcftimemodonvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);
        
        comp = new LabelledDial("Release Vel", this, "f" + filter + "dcftimemodoffvel", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);

        comp = new LabelledDial("Key Scaling", this, "f" + filter + "dcftimemodks", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Time Mod");
        hbox.add(comp);
        
        VBox vbox = new VBox();
        comp = new CheckBox("LFO", this, "f" + filter + "lfosw");
        vbox.add(comp);
        hbox.add(vbox);

        category.add(hbox, BorderLayout.CENTER);
        return category;
        }

    public JComponent addFilterEnvelope(int filterenv, Color color)
        {
        Category category = new Category("Filter Envelope " + filterenv, color);
                
        JComponent comp;
        String[] params;
        final HBox hbox = new HBox();

        comp = new LabelledDial("Depth", this,  "f" + filterenv + "dcfenvdep", color, 0, 100, 50);
        hbox.add(comp);

        comp = new LabelledDial("Attack", this, "f" + filterenv + "dcfenvattack", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Decay", this, "f" + filterenv + "dcfenvdecay", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Sustain", this, "f" + filterenv + "dcfenvsustain", color, 0, 100);
        hbox.add(comp);

        comp = new LabelledDial("Release", this, "f" + filterenv + "dcfenvrelease", color, 0, 100);
        hbox.add(comp);
                        
        comp = new LabelledDial("Velocity", this,  "f" + filterenv + "dcfenvveldep", color, 0, 100, 50);
        ((LabelledDial)comp).setSecondLabel("Depth Mod");
        hbox.add(comp);
                
        comp = new EnvelopeDisplay(this, Color.red, 
            new String[] { null, "f" + filterenv + "dcfenvattack", "f" + filterenv + "dcfenvdecay", null, "f" + filterenv + "dcfenvrelease" },
            new String[] { null, null, "f" + filterenv + "dcfenvsustain", "f" + filterenv + "dcfenvsustain", null },
            new double[] { 0, 0.25/100.0, 0.25 / 100.0,  0.25, 0.25/100.0},
            new double[] { 0, 1.0, 1.0 / 100.0, 1.0/100.0, 0 });
        hbox.addLast(comp);
                 
        category.add(hbox, BorderLayout.CENTER);
        return category;
        }


	HashMap allK4ParametersToIndex = new HashMap();
	
	final static String[] allK4Parameters = new String[]
		{
		"name1",
		"name2",
		"name3",
		"name4",
		"name5",
		"name6",
		"name7",
		"name8",
		"name9",
		"name10",
		"volume",
		"effect",
		"outselect",
		"sourcemode",
		"polymode",
		"ams1>s2",
		"ams3>s4",
		"s1mute",
		"s2mute",
		"s3mute",
		"s4mute",
		"vibshape",
		"pitchbend",
		"wheelassign",
		"vibspeed",
		"wheeldep",
		"autobendtime",
		"autobenddepth",
		"autobendks>time",
		"autobendvel>dep",
		"vibprs>vib",
		"vibratodep",
		"lfoshape",
		"lfospeed",
		"lfodelay",
		"lfodep",
		"lfoprs>dep",
		"pres>freq",
		"s:delay",
		"s:waveselecthi",
		"s:kscurve",
		"s:waveselectlo",
		"s:coarse",
		"s:keytrack",
		"s:fix",
		"s:fine",
		"s:prs>frqsw",
		"s:vib/a.bendsw",
		"s:velcurve",
		"s:envelopelevel",
		"s:envelopeattack",
		"s:envelopedecay",
		"s:envelopesustain",
		"s:enveloperelease",
		"s:levelmodvel",
		"s:levelmodks",
		"s:timemodonvel",
		"s:timmodoffvel",
		"s:timemodks",
		"f:cutoff",
		"f:resonance",
		"f:lfosw",
		"f:cutoffmodvel",
		"f:cutoffmodprs",
		"f:cutoffmodks",
		"f:dcfenvdep",
		"f:dcfenvveldep",
		"f:dcfenvattack",
		"f:dcfenvdecay",
		"f:dcfenvsustain",
		"f:dcfenvrelease",
		"f:dcftimemodonvel",
		"f:dcftimemodoffvel",
		"f:dcftimemodks"
		};


    /** Map of parameter -> index in the allParameters array. */
    HashMap allParametersToIndex = new HashMap();

    /** List of all Oberheim Sysex parameters in order.  "-" is a reserved (unused and thus unnamed) parameter. */

    /// * indicates parameters which must be handled specially due to packing
    /// that Waldorf decided to do.  :-(

    final static String[] allParameters = new String[/*100 or so*/] 
    {
    "-",                // this is the name
    "-",
    "-",
    "-",
    "-",
    "-",
    "-",
    "-",
    "-",
    "-",
    "volume",                   
    "effect",
    "outselect",
    "sourcemode_polymod_ams1>s2_ams3>s4",		// *
    "s1mute_s2mute_s3mute_s4mute_vibshape",     // *
    "pitchbend_wheelassign",     				// *
    "vibspeed",
    "wheeldep",
    "autobendtime",
    "autobenddepth",           
    "autobendks>time",        
    "autobendvel>dep",
    "vibprs>vib",
    "vibratodep",           
    "lfoshape",
    "lfospeed",           
    "lfodelay",
    "lfodep",
    "lfoprs>dep",
    "pres>freq",
    "s1delay",                
    "s2delay",                
    "s3delay",
    "s4delay",
    "s1waveselecthi_kscurve",		//*
    "s2waveselecthi_kscurve",       //*       
    "s3waveselecthi_kscurve",		//*
    "s4waveselecthi_kscurve",		//*
    "s1waveselectlo",				// *
    "s2waveselectlo",				// *
    "s3waveselectlo",				// *
    "s4waveselectlo",				// *
    "s1coarse_keytrack",				//*
    "s2coarse_keytrack",				//*
    "s3coarse_keytrack",				//*
    "s4coarse_keytrack",				//*
    "s1fix",
    "s2fix",
    "s3fix",
    "s4fix",
    "s1fine",
    "s2fine",
    "s3fine",
    "s4fin3",
    "s1prs>frqsw_vib/a.bendsw_velcurve",		//*
    "s2prs>frqsw_vib/a.bendsw_velcurve",		//*
    "s3prs>frqsw_vib/a.bendsw_velcurve",		//*
    "s4prs>frqsw_vib/a.bendsw_velcurve",		//*
    "s1envelopelevel",
    "s2envelopelevel",
    "s3envelopelevel",
    "s4envelopelevel",
    "s1envelopeattack",
    "s2envelopeattack",
    "s3envelopeattack",
    "s4envelopeattack",
    "s1envelopedecay",
    "s2envelopedecay",
    "s3envelopedecay",
    "s4envelopedecay",
    "s1envelopesustain",
    "s2envelopesustain",
    "s3envelopesustain",
    "s4envelopesustain",
    "s1enveloperelease",
    "s2enveloperelease",
    "s3enveloperelease",
    "s4enveloperelease",
    "s1levelmodevel",
    "s2levelmodevel",
    "s3levelmodevel",
    "s4levelmodevel",
    "s1levelmodprs",
    "s2levelmodprs",
    "s3levelmodprs",
    "s4levelmodprs",
    "s1levelmodks",
    "s2levelmodks",
    "s3levelmodks",
    "s4levelmodks",
    "s1timemodonvel",
    "s2timemodonvel",
    "s3timemodonvel",
    "s4timemodonvel",
    "s1timemodoffvel",
    "s2timemodoffvel",
    "s3timemodoffvel",
    "s4timemodoffvel",
    "s1timemodks",
    "s2timemodks",
    "s3timemodks",
    "s4timemodks",
    "f1cutoff",
    "f2cuttoff",
    "f1resonance_lfosw",		// *
    "f2resonance_lfosw",		// *
    "f1cutoffmodvel",
    "f2cutoffmodvel",
    "f1cutoffmodprs",
    "f2cutoffmodprs",
    "f1cutoffmodks",
    "f2cutoffmodks",
    "f1dcfenvdep",
    "f2dcfenvdep",
    "f1dcfenvveldep",
    "f2dcfenvveldep",
    "f1dcfenvattack",
    "f2dcfenvattack",
    "f1dcfenvsustain",
    "f2dcfenvsustain",
    "f1dcfenvrelease",
    "f2dcfenvrelease",
    "f1dcftimemodonvel",
    "f2dcftimemodonvel",
    "f1dcftimemodoffvel",
    "f2dcftimemodoffvel",
    "f1dcftimemodks",
    "f2dcftimemodks"
    };



    public byte[] emit(String key)
        {
        if (key.equals("bank")) return new byte[0];  // this is not emittable
        if (key.equals("number")) return new byte[0];  // this is not emittable

		if (key.equals("name"))
			{
			String name = model.get("key", "Untitled");
			while(name.length() < 10)
				name = name + " ";
				
			byte[] b = new byte[10 * 10];
			for(int i = 0; i < 10; i ++)
				{
				byte[] data = { (byte)0xF0, 0x40, (byte)getChannelOut(), 0x10, 0x00, 0x04, (byte)i, 0x0, (byte)(name.charAt(i)), (byte)0xF7 };
				System.arraycopy(data, 0, b, 10 * i, 10);
				}
			return b;
			}
		else 
			{
			int source = 0;
			String newkey = key;
			byte msb = (byte)(model.get(key, 0) >> 7);		// particularly for "waveselect"
			byte lsb = (byte)(model.get(key, 0) & 127);

			if (key.startsWith("s1"))
				{
				source = 1;
				newkey = "s:" + key.substring(2);
				}
			else if (key.startsWith("s2"))
				{
				source = 2;
				newkey = "s:" + key.substring(2);
				}
			else if (key.startsWith("s3"))
				{
				source = 3;
				newkey = "s:" + key.substring(2);
				}
			else if (key.startsWith("s4"))
				{
				source = 4;
				newkey = "s:" + key.substring(2);
				}
			else if (key.startsWith("f1"))
				{
				source = 1;
				newkey = "f:" + key.substring(2);
				}
			else if (key.startsWith("f2"))
				{
				source = 2;
				newkey = "f:" + key.substring(2);
				}
				
			int index = ((Integer)(allK4ParametersToIndex.get(newkey))).intValue();

			return new byte[] { (byte)0xF0, 0x40, (byte)getChannelOut(), 0x10, 0x00, 0x04, (byte)index, (byte)((source << 1) | msb), (byte)lsb, (byte)0xF7 };
			}
        }
    


    public void parseParameter(byte[] data)
        {
        // This doesn't happen
        }
        
    public boolean parse(byte[] data, boolean ignorePatch)
        {
		if (data.length == 7 &&			// write error report
			data[0] == (byte)0xF0 &&
			data[1] == (byte)0x40 &&
			data[3] >= (byte)0x41 &&
			data[3] <= (byte)0x43 &&
			data[4] == (byte)0x00 &&
			data[5] == (byte)0x04)
			{
			String error = "Write Failed (Maybe Transmission Failure)";
			// dump failed
			if (data[3] == 0x42)
				error = "Patch is Write-Protected";
			else if (data[3] == 0x43)
				error = "External Data Card is Not Inserted";
			
	        JOptionPane.showMessageDialog(this, error, "Write Failed", JOptionPane.ERROR_MESSAGE);
			return true;
			}
		else 
			{
			if (data[7] > 64)  // it's multi, not sure how that happened
				{ new RuntimeException("Multi provided to parse, which shouldn't happen").printStackTrace(); return false; }
			
			model.set("bank", (data[7] / 16) + (data[6] == 0x00 ? 0 : 4));
			model.set("number", data[7] % 16);
			
			byte[] name = new byte[10];

    	// The K4 is riddled with byte-mangling.  :-(
    	
    	for(int i = 0; i < 130; i++)
    		{
    		String key = allParameters[i];
    		    		
			if (i < 10)  // name
				{
				name[i] = data[i + 8];
				}
			else if (key.equals("sourcemode_polymod_ams1>s2_ams3>s4"))
				{
				model.set("sourcemode", data[i + 8] & 3);
				model.set("polymode", (data[i + 8] >> 2) & 3);
				model.set("ams1>s2", (data[i + 8] >> 4) & 1);
				model.set("ams3>s4", data[i + 8] >> 5);
				}
			else if (key.equals("s1mute_s2mute_s3mute_s4mute_vibshape"))
				{
				model.set("s1mute", data[i + 8] & 1);
				model.set("s2mute", (data[i + 8] >> 1) & 1);
				model.set("s3mute", (data[i + 8] >> 2) & 1);
				model.set("s4mute", (data[i + 8] >> 3) & 1);
				model.set("vibshape", data[i + 8] >> 4);
				}
			else if (key.equals("pitchbend_wheelassign"))
				{
				model.set("pitchbend", data[i + 8] & 31);
				model.set("wheelassign", data[i + 8] >> 4);
				}
			else if (key.equals("s1waveselecthi_kscurve"))
				{
				model.set("s1waveselect", (data[i + 8] << 7) | (data[i + 8 + 1]));		// hi and lo
				model.set("s1kscurve", data[i + 8] >> 4);
				}
			else if (key.equals("s2waveselecthi_kscurve"))
				{
				model.set("s2waveselect", (data[i + 8] << 7) | (data[i + 8 + 1]));		// hi and lo
				model.set("s2kscurve", data[i + 8] >> 4);
				}
			else if (key.equals("s3waveselecthi_kscurve"))
				{
				model.set("s3waveselect", (data[i + 8] << 7) | (data[i + 8 + 1]));		// hi and lo
				model.set("s3kscurve", data[i + 8] >> 4);
				}
			else if (key.equals("s4waveselecthi_kscurve"))
				{
				model.set("s4waveselect", (data[i + 8] << 7) | (data[i + 8 + 1]));		// hi and lo
				model.set("s4kscurve", data[i + 8] >> 4);
				}
			else if (key.equals("s1waveselectlo"))
				{
				// do nothing, already done
				}
			else if (key.equals("s2waveselectlo"))
				{
				// do nothing, already done
				}
			else if (key.equals("s3waveselectlo"))
				{
				// do nothing, already done
				}
			else if (key.equals("s4waveselectlo"))
				{
				// do nothing, already done
				}
			else if (key.equals("s1coarse_keytrack"))
				{
				model.set("s1coarse", data[i + 8] & 63);
				model.set("s1keytrack", data[i + 8] >> 6);
				}
			else if (key.equals("s2coarse_keytrack"))
				{
				model.set("s2coarse", data[i + 8] & 63);
				model.set("s2keytrack", data[i + 8] >> 6);
				}
			else if (key.equals("s3coarse_keytrack"))
				{
				model.set("s3coarse", data[i + 8] & 63);
				model.set("s3keytrack", data[i + 8] >> 6);
				}
			else if (key.equals("s4coarse_keytrack"))
				{
				model.set("s4coarse", data[i + 8] & 63);
				model.set("s4keytrack", data[i + 8] >> 6);
				}
			else if (key.equals("s1prs>frqsw_vib/a.bendsw_velcurve"))
				{
				model.set("s1prs>frqsw", data[i + 8] & 1);
				model.set("s1vib/a.bend", (data[i + 8] >> 1) & 1);
				model.set("s1velcurve", data[i + 8] >> 2);
				}
			else if (key.equals("s2prs>frqsw_vib/a.bendsw_velcurve"))
				{
				model.set("s2prs>frqsw", data[i + 8] & 1);
				model.set("s2vib/a.bend", (data[i + 8] >> 1) & 1);
				model.set("s2velcurve", data[i + 8] >> 2);
				}
			else if (key.equals("s3prs>frqsw_vib/a.bendsw_velcurve"))
				{
				model.set("s3prs>frqsw", data[i + 8] & 1);
				model.set("s3vib/a.bend", (data[i + 8] >> 1) & 1);
				model.set("s3velcurve", data[i + 8] >> 2);
				}
			else if (key.equals("s4prs>frqsw_vib/a.bendsw_velcurve"))
				{
				model.set("s4prs>frqsw", data[i + 8] & 1);
				model.set("s4vib/a.bend", (data[i + 8] >> 1) & 1);
				model.set("s4velcurve", data[i + 8] >> 2);
				}
			else if (key.equals("f1resonance_2fosw"))
				{
				model.set("f1resonance", data[i + 8] & 1);
				model.set("f1lfosw", data[i + 8] >> 3);
				}
			else if (key.equals("f1resonance_2fosw"))
				{
				model.set("f2resonance", data[i + 8] & 1);
				model.set("f2lfosw", data[i + 8] >> 3);
				}
			else
				{
				model.set(key, data[i + 8]);
				}
			}

			model.set("name", new String(name));

	        revise();
    	    return true;            // change this as appropriate
			}
        }
    
    /** Generate a K4 checksum of the data bytes */
    byte produceChecksum(byte[] bytes)
        {
        //      The K4 manual says the checksum is the
        //              "Sum of the A5H and s0~s129".
        //		I believe this is A5 + sum(s0...s129) ignoring overflow, cut to 7 bits

        int checksum = 0xA5;
        for(int i = 0; i < bytes.length; i++)
            checksum = (checksum + bytes[i]) & 255;
        return (byte)(checksum & 127);
        }

    public byte[] emit(Model tempModel, boolean toWorkingMemory)
        {
    	byte[] data = new byte[130];
    
    	String name = model.get("name", "Untitled");
		while(name.length() < 10)
			name = name + " ";
    	
    	// The K4 is riddled with byte-mangling.  :-(
    	
    	for(int i = 0; i < 130; i++)
    		{
    		String key = allParameters[i];
    		    		
			if (i < 10)  // name
				{
				data[i] = (byte)name.charAt(i);
				}
			else if (key.equals("sourcemode_polymod_ams1>s2_ams3>s4"))
				{
				data[i] = (byte)(model.get("sourcemode", 0) | (model.get("polymode", 0) << 2) | (model.get("ams1>s2", 0) << 4) | (model.get("ams3>s4", 0) << 5));
				}
			else if (key.equals("s1mute_s2mute_s3mute_s4mute_vibshape"))
				{
				data[i] = (byte)(model.get("s1mute", 0) | (model.get("s2mute", 0) << 1) | (model.get("s3mute", 0) << 2) | (model.get("s4mute", 0) << 3) | (model.get("vibshape", 0) << 4));
				}
			else if (key.equals("pitchbend_wheelassign"))
				{
				data[i] = (byte)(model.get("pitchbend", 0) | (model.get("wheelassign", 0) << 4));
				}
			else if (key.equals("s1waveselecthi_kscurve"))
				{
				data[i] = (byte)((model.get("s1waveselect", 0) >> 7) | 	// hi bit put in lo position
							(model.get("s1kscurve", 0) << 4));
				}
			else if (key.equals("s2waveselecthi_kscurve"))
				{
				data[i] = (byte)((model.get("s2waveselect", 0) >> 7) | 	// hi bit put in lo position
							(model.get("s2kscurve", 0) << 4));
				}
			else if (key.equals("s3waveselecthi_kscurve"))
				{
				data[i] = (byte)((model.get("s3waveselect", 0) >> 7) | 	// hi bit put in lo position
							(model.get("s3kscurve", 0) << 4));
				}
			else if (key.equals("s4waveselecthi_kscurve"))
				{
				data[i] = (byte)((model.get("s4waveselect", 0) >> 7) | 	// hi bit put in lo position
							(model.get("s4kscurve", 0) << 4));
				}
			else if (key.equals("s1waveselectlo"))
				{
				data[i] = (byte)(model.get("s1waveselect", 0) & 127);
				}
			else if (key.equals("s2waveselectlo"))
				{
				data[i] = (byte)(model.get("s2waveselect", 0) & 127);
				}
			else if (key.equals("s3waveselectlo"))
				{
				data[i] = (byte)(model.get("s3waveselect", 0) & 127);
				}
			else if (key.equals("s4waveselectlo"))
				{
				data[i] = (byte)(model.get("s4waveselect", 0) & 127);
				}
			else if (key.equals("s1coarse_keytrack"))
				{
				data[i] = (byte)(model.get("s1coarse", 0) | (model.get("s1keytrack", 0) << 6));
				}
			else if (key.equals("s2coarse_keytrack"))
				{
				data[i] = (byte)(model.get("s2coarse", 0) | (model.get("s2keytrack", 0) << 6));
				}
			else if (key.equals("s3coarse_keytrack"))
				{
				data[i] = (byte)(model.get("s3coarse", 0) | (model.get("s3keytrack", 0) << 6));
				}
			else if (key.equals("s4coarse_keytrack"))
				{
				data[i] = (byte)(model.get("s4coarse", 0) | (model.get("s4keytrack", 0) << 6));
				}
			else if (key.equals("s1prs>frqsw_vib/a.bendsw_velcurve"))
				{
				data[i] = (byte)(model.get("s1prs>frqsw", 0) | (model.get("s1vib/a.bend", 0) << 1) | (model.get("s1velcurve", 0) << 2));
				}
			else if (key.equals("s2prs>frqsw_vib/a.bendsw_velcurve"))
				{
				data[i] = (byte)(model.get("s2prs>frqsw", 0) | (model.get("s2vib/a.bend", 0) << 1) | (model.get("s2velcurve", 0) << 2));
				}
			else if (key.equals("s3prs>frqsw_vib/a.bendsw_velcurve"))
				{
				data[i] = (byte)(model.get("s3prs>frqsw", 0) | (model.get("s3vib/a.bend", 0) << 1) | (model.get("s3velcurve", 0) << 2));
				}
			else if (key.equals("s4prs>frqsw_vib/a.bendsw_velcurve"))
				{
				data[i] = (byte)(model.get("s4prs>frqsw", 0) | (model.get("s4vib/a.bend", 0) << 1) | (model.get("s4velcurve", 0) << 2));
				}
			else if (key.equals("f1resonance_lfosw"))
				{
				data[i] = (byte)(model.get("f1resonance", 0) | (model.get("f1lfosw", 0) << 3));
				}
			else if (key.equals("f2resonance_2fosw"))
				{
				data[i] = (byte)(model.get("f2resonance", 0) | (model.get("f2lfosw", 0) << 3));
				}
			else
				{
				data[i] = (byte)(model.get(key, 0));
				}
			}

		// Error in Section 4-1, see "Corrected MIDI Implementation"

        boolean external = (model.get("bank", 0) > 4);
		byte position = (byte)((model.get("bank", 0) % 2) * 16 + (model.get("number", 0)));  // 0...63 for A1 .... D16
		byte[] result = new byte[EXPECTED_SYSEX_LENGTH];
		result[0] = (byte)0xF0;
		result[1] = (byte)0x40;
		result[2] = (byte)getChannelOut();
		if (toWorkingMemory)
			result[3] = (byte)0x23;
		else
			result[3] = (byte)0x20;
		result[4] = (byte)0x00;
		result[5] = (byte)0x04;
		if (toWorkingMemory)
			result[6] = 0x00;
		else
			result[6] = (byte)(external ? 0x02 : 0x00);
		if (toWorkingMemory)
			result[7] = (byte)(0x40);  // error in the manual, it should be 0x000000 not 000x0000
		else
			result[7] = (byte)position;
		System.arraycopy(data, 0, result, 0, 130);
		result[8] = (byte)produceChecksum(data);
		result[9] = (byte)0xF7;
		return result;
        }


    public byte[] requestDump(Model tempModel)
        {
        boolean external = (model.get("bank", 0) > 4);
		byte position = (byte)((model.get("bank", 0) % 2) * 16 + (model.get("number", 0)));  // 0...63 for A1 .... D16
        return new byte[] { (byte)0xF0, 0x40, (byte)getChannelOut(), 0x00, 0x00, 0x04, 
        			(byte)(external ? 0x02 : 0x00),
        			position, (byte)0xF7};
        }
                
    public byte[] requestCurrentDump(Model tempModel)
        {
        // We can't do this
        return new byte[0];
        }

    public static boolean recognize(byte[] data)
        {
        return ((
            			data.length == EXPECTED_SYSEX_LENGTH &&
            			data[0] == (byte)0xF0 &&
            			data[1] == (byte)0x40 &&
            			data[3] == (byte)0x21 &&
            			data[4] == (byte)0x00 &&
            			data[5] == (byte)0x04) ||
            		(
            			data.length == 7 &&			// write error report
            			data[0] == (byte)0xF0 &&
            			data[1] == (byte)0x40 &&
            			data[3] >= (byte)0x41 &&
            			data[3] <= (byte)0x43 &&
            			data[4] == (byte)0x00 &&
            			data[5] == (byte)0x04 &&
            			data[7] <= 63));  // single patch
        }
        

    public static final int EXPECTED_SYSEX_LENGTH = 140;        
        
    /** Verify that all the parameters are within valid values, and tweak them if not. */
    public void revise()
        {
        // check the easy stuff -- out of range parameters
        super.revise();

        // handle "name" specially
        StringBuffer name = new StringBuffer(model.get("name", "Untitled  "));  // has to be 10 long
        for(int i = 0; i < name.length(); i++)
            {
            char c = name.charAt(i);
            if (c < 32 || c > 127)
                { name.setCharAt(i, (char)32); System.err.println("Warning: Revised name from \"" + model.get("name", "Untitled  ") + "\" to \"" + name.toString() + "\"");}
            }
        model.set("name", name.toString());
        }
        
    public boolean requestCloseWindow() { return true; }

    public static String getSynthName() { return "Kawai K4"; }
    
    public String getPatchName() { return model.get("name", "Untitled  "); }  // has to be 10 long
    }
